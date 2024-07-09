package com.example.projectfinalandroid

import NoteRecyclerViewAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectfinalandroid.databinding.FragmentFirstBinding
import com.example.projectfinalandroid.models.Note
import com.example.projectfinalandroid.models.NoteViewModel
import com.example.projectfinalandroid.models.NoteViewModelFactory

class FirstFragment : Fragment() {
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

        noteAdapter = NoteRecyclerViewAdapter(emptyList()) {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            viewModel.selectNote(it)
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
