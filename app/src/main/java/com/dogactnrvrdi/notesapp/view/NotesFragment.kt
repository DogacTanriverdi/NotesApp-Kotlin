package com.dogactnrvrdi.notesapp.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.adapter.NoteRecyclerAdapter
import com.dogactnrvrdi.notesapp.databinding.FragmentNotesBinding
import com.dogactnrvrdi.notesapp.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment :
    Fragment(R.layout.fragment_notes),
    SearchView.OnQueryTextListener,
    MenuProvider {

    // Binding
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: NoteViewModel by viewModels()

    // Adapter
    private lateinit var noteRecyclerAdapter: NoteRecyclerAdapter

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

        activity?.addMenuProvider(this)

        val fab = binding.addNoteFab

        binding.notesRV.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY > oldScrollY + 12 && fab.isExtended) {
                fab.shrink()
            }
            if (scrollY < oldScrollY - 12 && !fab.isExtended) {
                fab.extend()
            }
        }
    }

    // RecyclerView Setup
    private fun setupRecyclerView() {
        noteRecyclerAdapter = NoteRecyclerAdapter()
        binding.notesRV.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = noteRecyclerAdapter
        }

        noteRecyclerAdapter.setOnItemClickListener {
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
                val currentNote = noteRecyclerAdapter.notes[itemPosition]

                viewModel.deleteNote(currentNote)

                Snackbar.make(
                    requireView(),
                    getString(R.string.note_deleted_successfully),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.undo)) {
                    viewModel.insertNote(currentNote)
                }.show()
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.notesRV)
    }

    // Subscribe To Observers/*
    private fun subscribeToObservers() {
        viewModel.notes.observe(viewLifecycleOwner) { list ->
            noteRecyclerAdapter.recyclerListDiffer.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.removeMenuProvider(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null)
            searchNotes(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null)
            searchNotes(newText)
        return true
    }

    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"
        viewModel.searchNote(searchQuery).observe(viewLifecycleOwner) { list ->
            noteRecyclerAdapter.recyclerListDiffer.submitList(list)
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.queryHint = requireContext().getString(R.string.search_in_notes)
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }
}