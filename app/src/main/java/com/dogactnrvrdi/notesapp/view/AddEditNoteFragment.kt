package com.dogactnrvrdi.notesapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.FragmentAddEditNoteBinding
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    // Binding
    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    // ViewModel
    private val viewModel: NoteViewModel by viewModels()

    // NavArgs
    private val args: AddEditNoteFragmentArgs by navArgs()

    // Current Note
    private var currentNote: Note? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        _binding = FragmentAddEditNoteBinding.bind(view)

        // Current Note
        currentNote = args.note

        currentNote?.let { note ->
            binding.apply {
                noteTitleET.setText(note.title)
                noteBodyET.setText(note.description)
                noteBodyET.requestFocus()
                val lastModifiedString = "${R.string.last_modified} ${note.createdDateFormatted}"
                lastModifiedTV.text = lastModifiedString
                lastModifiedTV.visibility = View.VISIBLE
            }
        }

        // Save Note Fab
        binding.fabSave.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        binding.apply {
            val title = noteTitleET.text.trim().toString()
            val description = noteBodyET.text?.trim().toString()

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Title or description cannot be empty!",
                    Toast.LENGTH_LONG
                ).show()
                return
            }

            currentNote?.let {
                if (title == it.title && description == it.description) {
                    return
                }
            }

            val newNote = if (currentNote == null) {
                Note(title, description)
            } else {
                currentNote?.apply {
                    this.title = title
                    this.description = description
                    this.created = System.currentTimeMillis()
                }
            }

            if (newNote != null) {
                viewModel.insertNote(newNote)
                Toast.makeText(
                    requireContext(), R.string.saved, Toast.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        /*
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle(R.string.want_to_quit)
        alert.setMessage(R.string.notes_will_be_deleted)
        alert.setPositiveButton(R.string.yes) { p0, p1 ->
            Toast.makeText(
                requireContext(), R.string.not_saved, Toast.LENGTH_SHORT
            ).show()
        }
        alert.setNegativeButton(R.string.no) { p0, p1 ->
            Toast.makeText(
                requireContext(),
                "Continue",
                Toast.LENGTH_SHORT
            ).show()
        }
        alert.show()

         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}