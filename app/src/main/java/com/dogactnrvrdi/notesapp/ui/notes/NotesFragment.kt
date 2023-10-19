package com.dogactnrvrdi.notesapp.ui.notes

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.FragmentNotesBinding
import com.dogactnrvrdi.notesapp.ui.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesFragment :
    Fragment(R.layout.fragment_notes),
    SearchView.OnQueryTextListener,
    MenuProvider {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    private val noteRecyclerAdapter by lazy { NoteRecyclerAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesBinding.bind(view)

        with(binding) {

            addNoteFab.setOnClickListener {
                val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment()
                findNavController().navigate(action)
            }

            activity?.addMenuProvider(this@NotesFragment)

            notesRV.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

                if (scrollY > oldScrollY + 12 && addNoteFab.isExtended) {
                    addNoteFab.shrink()
                }

                if (scrollY < oldScrollY - 12 && !addNoteFab.isExtended) {
                    addNoteFab.extend()
                }
            }
        }

        setupRecyclerView()

        observeData()
    }

    private fun observeData() {
        viewModel.notes.observe(viewLifecycleOwner) { noteList ->
            noteRecyclerAdapter.recyclerListDiffer.submitList(noteList)
        }
    }

    private fun searchNotes(query: String?) {
        with(viewModel) {
            val searchQuery = "%$query%"
            searchNote(searchQuery).observe(viewLifecycleOwner) { note ->
                noteRecyclerAdapter.recyclerListDiffer.submitList(note)
            }
        }
    }

    private fun setupRecyclerView() {
        binding.notesRV.adapter = noteRecyclerAdapter

        noteRecyclerAdapter.setOnItemClickListener {
            val action = NotesFragmentDirections.actionNotesFragmentToAddEditNoteFragment(it)
            findNavController().navigate(action)
        }
        setSwipeToDelete()
    }

    private fun setSwipeToDelete() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.removeMenuProvider(this)
    }
}