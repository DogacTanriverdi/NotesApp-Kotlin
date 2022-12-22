package com.dogactnrvrdi.notesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.adapter.NoteListAdapter
import com.dogactnrvrdi.notesapp.databinding.FragmentNotesBinding
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    // Binding
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: NoteViewModel by viewModels()

    // Adapter
    //private lateinit var noteRecyclerAdapter: NoteRecyclerAdapter
    private lateinit var noteListAdapter: NoteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        _binding = FragmentNotesBinding.bind(view)

        binding.addNoteFab.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment())
        }

        //RecyclerView Setup
        setupRecyclerView()

        // Subscribe to Observers
        //subscribeToObservers()
        createFakeNotes()
    }

    // RecyclerView Setup
    private fun setupRecyclerView() {
        noteListAdapter = NoteListAdapter()
        binding.notesRV.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = noteListAdapter
        }

        noteListAdapter.setOnItemClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(it)
            findNavController().navigate(action)
        }
    }

    private fun createFakeNotes() {
        val fakeNoteList = listOf(
            Note(id = 1, "Not Deneme 1", "Lorem Ipsum mipsum siksok şeyler"),
            Note(id = 2, "Not Deneme 2", "Lorem Ipsum mipsum siksok şeyler"),
            Note(id = 3, "Avm Gidilecek", "Going to fuckin AVM Mann"),
            Note(id = 4, "WUHUU", "Lorem naber Ipsum mipsum siksok şeyler"),
            Note(id = 5, "Not Deneme 31", "Lorem gahfshsf Ipsum mipsum siksok şeyler"),
            Note(id = 6, "Not Deneme 69", "Lorem otuzbir6doksfgsfguz Ipsum mipssgsfgsfum sikssfhsgsfgsfh5700023500096500ok şeyler"),
            Note(id = 6, "Not Deneme 69", "Lorem otuzbir6doksfgsfguz Ipsum mipssgsfgsfum sikssfhsgsfgsfh5700023500096500ok şeyler"),
            Note(id = 6, "Not Deneme 69", "Lorem otuzbir6doksfgsfguz Ipsum mipssgsfgsfum sikssfhsgsfgsfh5700023500096500ok şeyler")
        )
        noteListAdapter.submitList(fakeNoteList)
    }

    // Subscribe To Observers
    /*
    private fun subscribeToObservers() {
        viewModel.notes.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
        }
    }

     */

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}