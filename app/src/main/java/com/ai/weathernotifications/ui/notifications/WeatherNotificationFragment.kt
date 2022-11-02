package com.ai.weathernotifications.ui.notifications

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ai.weathernotifications.data.db.WeatherNotification
import com.ai.weathernotifications.databinding.FragmentItemListBinding
import com.ai.weathernotifications.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class WeatherNotificationFragment : Fragment() {

    private val notificationsViewModel:
            WeatherNotificationsViewModel by viewModels()
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentAdapter: WeatherNotificationRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        currentAdapter = WeatherNotificationRecyclerViewAdapter {
            findNavController().navigate(
                WeatherNotificationFragmentDirections.actionWeatherNotificationToWeatherNotificationDetails(
                    it.id
                )
            )
        }
        return binding.root
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding.list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = currentAdapter
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            notificationsViewModel.weatherData.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        currentAdapter.submitList(result.data)
                    }
                    is Resource.Error -> {
                        result.data?.let {
                            currentAdapter.submitList(it)
                        }
                        Snackbar.make(
                            binding.list,
                            result.error?.localizedMessage.orEmpty(),
                            Snackbar.LENGTH_LONG
                        )
                            .show()
                    }
                    is Resource.Loading -> {
                        result.data?.let {
                            currentAdapter.submitList(it)
                        }
                    }
                    null -> Unit
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}