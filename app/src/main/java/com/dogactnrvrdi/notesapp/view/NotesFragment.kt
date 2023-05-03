package com.dogactnrvrdi.notesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.adapter.NoteListAdapter
import com.dogactnrvrdi.notesapp.databinding.FragmentNotesBinding
import com.dogactnrvrdi.notesapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment : Fragment(R.layout.fragment_notes) {

    // Binding
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: NoteViewModel by viewModels()

    // Adapter
    private lateinit var noteListAdapter: NoteListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        _binding = FragmentNotesBinding.bind(view)

        binding.addNoteFab.setOnClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
            findNavController().navigate(action)
        }

        //RecyclerView Setup
        setupRecyclerView()

        // Subscribe to Observers
        subscribeToObservers()
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
        setSwipeToDelete()
    }

    // Swipe to Delete
    private fun setSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemPosition = viewHolder.adapterPosition
                val currentNote = noteListAdapter.currentList[itemPosition]

                viewModel.deleteNote(currentNote)

                Snackbar.make(
                    requireView(), R.string.note_deleted_successfully, Snackbar.LENGTH_LONG
                ).setAction(R.string.undo) {
                    viewModel.insertNote(currentNote)
                }.show()
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.notesRV)
    }

    // Subscribe To Observers/*
    private fun subscribeToObservers() {
        viewModel.notes.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}