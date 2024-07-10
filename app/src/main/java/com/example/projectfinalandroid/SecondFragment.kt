package com.example.projectfinalandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.projectfinalandroid.databinding.FragmentSecondBinding
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.models.NoteViewModel

class SecondFragment : Fragment() {
    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: NoteViewModel

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
        }

        binding.buttonSave.setOnClickListener {
            viewModel.title.value = binding.editTitle.text.toString()
            viewModel.description.value = binding.editDescription.text.toString()
            viewModel.save()

            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.selectedNote?.let { note ->
                viewModel.delete(note)
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
}
