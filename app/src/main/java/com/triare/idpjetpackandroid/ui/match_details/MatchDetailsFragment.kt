package com.triare.idpjetpackandroid.ui.match_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.work.*
import by.kirich1409.viewbindingdelegate.CreateMethod
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.triare.idpjetpackandroid.databinding.FragmentMatchDetailsBinding
import com.triare.idpjetpackandroid.workmanager.ColorFilterWorker
import com.triare.idpjetpackandroid.workmanager.DownloadWorker
import com.triare.idpjetpackandroid.workmanager.worker_params.WorkerKeys.FILTER_URI

class MatchDetailsFragment : Fragment() {

    private val binding: FragmentMatchDetailsBinding by viewBinding(CreateMethod.INFLATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val downloadRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(
                        NetworkType.CONNECTED
                    )
                    .build()
            )
            .build()

        val colorFilterRequest = OneTimeWorkRequestBuilder<ColorFilterWorker>()
            .build()

        val workManager = WorkManager.getInstance(requireContext())


        binding.action.setOnClickListener {
            workManager
                .beginUniqueWork(
                    "download",
                    ExistingWorkPolicy.KEEP,
                    downloadRequest
                )
                .then(colorFilterRequest)
                .enqueue()

        }

        workManager
            .getWorkInfosForUniqueWorkLiveData("download")
            .observe(viewLifecycleOwner) {

                when (it[0].state) {
                    WorkInfo.State.RUNNING -> changeText("Downloading ...")
                    WorkInfo.State.SUCCEEDED -> changeText("Downloading SUCCEEDED")
                    WorkInfo.State.FAILED -> changeText("Downloading FAILED")
                    WorkInfo.State.CANCELLED -> changeText("Downloading CANCELLED")
                    WorkInfo.State.ENQUEUED -> changeText("Downloading ENQUEUED")
                    WorkInfo.State.BLOCKED -> changeText("Downloading BLOCKED")
                }
                when (it[1].state) {
                    WorkInfo.State.RUNNING -> changeTextFilter("Filter ...")
                    WorkInfo.State.SUCCEEDED -> changeTextFilter("Filter SUCCEEDED")
                    WorkInfo.State.FAILED -> changeTextFilter("Filter FAILED")
                    WorkInfo.State.CANCELLED -> changeTextFilter("Filter CANCELLED")
                    WorkInfo.State.ENQUEUED -> changeTextFilter("Filter ENQUEUED")
                    WorkInfo.State.BLOCKED -> changeTextFilter("Filter BLOCKED")
                }
                it.forEach { workInfo ->
                    if (workInfo != null) {
                        val value = workInfo.outputData.getString(FILTER_URI)
                        Glide.with(requireContext())
                            .load(value)
                            .into(binding.image)
                    }

                }
            }

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun changeText(text: String) {
        binding.state.text = text
    }

    private fun changeTextFilter(text: String) {
        binding.stateFilter.text = text
    }
}
