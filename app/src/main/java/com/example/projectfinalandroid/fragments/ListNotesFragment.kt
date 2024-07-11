package com.example.projectfinalandroid.fragments

import NoteRecyclerViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinalandroid.R
import com.example.projectfinalandroid.databinding.FragmentFirstBinding
import com.example.projectfinalandroid.viewModels.NoteViewModel
import com.example.projectfinalandroid.viewModels.NoteViewModelFactory

class ListNotesFragment : Fragment() {
    lateinit var noteAdapter: NoteRecyclerViewAdapter
    lateinit var viewModel: NoteViewModel
    private var _binding: FragmentFirstBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = NoteViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory).get(NoteViewModel::class.java)
        viewModel.getAllNotes()

        noteAdapter = NoteRecyclerViewAdapter(emptyList()) {note ->
            val bundle = Bundle().apply {
                putString("id", note.id)
                putString("dateCreated", note.dateCreated)
                putString("title", note.title)
                putString("description", note.description)
                putDouble("latitude", note.latitude)
                putDouble("longitude", note.longitude)
                putString("userId", note.userId)
            }

            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
            viewModel.selectNote(note)
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = noteAdapter
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            noteAdapter.updateNotes(notes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
