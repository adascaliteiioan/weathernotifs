package com.ai.weathernotifications.ui.notificationdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ai.weathernotifications.R
import com.ai.weathernotifications.databinding.FragmentWeatherNotificationDetailsBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class WeatherNotificationDetailsFragment : Fragment() {

    private val viewModel: WeatherNotificationsDetailsViewModel by viewModels()
    private var _binding: FragmentWeatherNotificationDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<WeatherNotificationDetailsFragmentArgs>()
    private var instructionsMaxLines = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentWeatherNotificationDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.fetchWeatherNotifInfo(args.notificationId).collect { notification ->
                notification?.let {
                    viewModel.fetchAffectedZones(notification.affectedZones.zones)
                    with(binding){
                        Glide.with(requireActivity())
                            .load(it.imageUrl)
                            .placeholder(R.drawable.ic_baseline_play_circle_outline_24)
                            .into(eventIv)
                        timeTv.text = "${it.startDate} - ${it.endDate}"
                        severityTv.text = it.severity
                        certaintyTv.text = it.certainty
                        urgencyTv.text = it.urgency
                        sourceTv.text = it.source
                        descriptionTv.text = it.description
                        if(it.instruction.isNullOrBlank()) {
                            instructionsTv.isVisible = false
                        } else {
                            instructionsTv.isVisible = true
                            instructionsTv.text = it.instruction
                            instructionsTv.setOnClickListener {
                                instructionsTv.maxLines = getInstructionsMaxLines()
                            }
                        }
//                        affectedZonesTv.text = it.affectedZones.zones.toString()
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.affectedZones.collect{ locations ->
                binding.affectedZonesTv.text = locations.toString()
            }
        }
    }

    private fun getInstructionsMaxLines(): Int {
        instructionsMaxLines = if(instructionsMaxLines == 2) {
            Int.MAX_VALUE
        } else {
            2
        }
        return instructionsMaxLines
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}