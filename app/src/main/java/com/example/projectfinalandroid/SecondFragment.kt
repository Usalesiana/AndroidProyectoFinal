package com.example.projectfinalandroid

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinalandroid.databinding.FragmentSecondBinding
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.models.NoteViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts


class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel
    private lateinit var locationProvider: FusedLocationProviderClient
    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                //si hay permiso
                tryGetLastLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                //si hay permiso
                tryGetLastLocation()
            }
            else -> {
                // no nos dieron permiso :(
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)
        // Recuperar la nota seleccionada del Bundle
        arguments?.let { bundle ->
            val id = bundle.getString("id")
            val dateCreated = bundle.getString("dateCreated")
            val title = bundle.getString("title")
            val description = bundle.getString("description")
            val latitude = bundle.getDouble("latitude")
            val longitude = bundle.getDouble("longitude")
            val userId = bundle.getString("userId")
            val note = Note(id!!, dateCreated!!, title!!, description!!, latitude, longitude, userId!!)
            viewModel.selectNote(note)
            binding.editTitle.setText(note.title)
            binding.editDescription.setText(note.description)
            binding.date.setText(note.dateCreated)
            binding.tvUserNote.setText(note.userId)
            binding.tvLatitude.setText(note.latitude.toString())
            binding.tvLongitude.setText(note.longitude.toString())

            // Disable location button for existing notes
            binding.locationBtn.isEnabled = false
        }

        locationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding.locationBtn.setOnClickListener {
            tryGetLastLocation()
        }

        binding.buttonSave.setOnClickListener {
            viewModel.title.value = binding.editTitle.text.toString()
            viewModel.description.value = binding.editDescription.text.toString()
            viewModel.save()

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.selectedNote?.let { note ->
                viewModel.delete(note.id)
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        // Clear the ViewModel data and input fields when the fragment is paused
        viewModel.clearSelectedNote()
        binding.editTitle.text.clear()
        binding.editDescription.text.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun tryGetLastLocation() {
        val hasFineLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasCoarseLocationPermission = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocationPermission && !hasCoarseLocationPermission) {
            permissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
            return
        }

        locationProvider.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                viewModel.setLocation(location.latitude, location.longitude)
                binding.tvLatitude.text = "${location.latitude}"
                binding.tvLongitude.text = "${location.longitude}"
            }
        }
    }
}
