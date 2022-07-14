package com.triare.idpjetpackandroid.workmanager

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.triare.idpjetpackandroid.App.Companion.CHANNEL_ID
import com.triare.idpjetpackandroid.R
import com.triare.idpjetpackandroid.data.remote.rest.Rest
import com.triare.idpjetpackandroid.workmanager.worker_params.WorkerKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.random.Random

@Suppress("BlockingMethodInNonBlockingContext")
class DownloadWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        startForegroundService()

        val response = Rest.apiService.downloadImage()

        response.body()?.let { body ->
            return withContext(Dispatchers.IO) {
                val file = File(context.cacheDir, "image.jpg")
                val outputStream = FileOutputStream(file)
                outputStream.use { stream ->
                    try {
                        stream.write(body.bytes())
                    } catch (e: IOException) {
                        return@withContext Result.failure(
                            workDataOf(
                                WorkerKeys.ERROR_MSS to e.localizedMessage
                            )
                        )
                    }
                }
                Result.success(
                    workDataOf(
                        WorkerKeys.IMAGE_URI to file.toUri().toString()
                    )
                )
            }
        }
        if (!response.isSuccessful) {
            if (response.code().toString().startsWith("5")) {
                return Result.retry()
            }
            return Result.failure(
                workDataOf(
                    WorkerKeys.ERROR_MSS to "Network Error"
                )
            )
        }
        return Result.failure(
            workDataOf(
                WorkerKeys.ERROR_MSS to "Unknown Error"
            )
        )
    }

    private suspend fun startForegroundService() {
        setForeground(
            ForegroundInfo(
                Random.nextInt(),
                NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_baseline_cloud_download_24)
                    .setContentText(context.getString(R.string.downloading))
                    .setContentTitle(context.getString(R.string.downloading_title))
                    .build()
            )
        )
    }
}