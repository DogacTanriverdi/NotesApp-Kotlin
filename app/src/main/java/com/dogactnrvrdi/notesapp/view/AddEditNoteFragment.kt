package com.dogactnrvrdi.notesapp.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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
    }

    private fun saveNote() {
        binding.apply {
            val title = noteTitleET.text.trim().toString()
            val description = noteBodyET.text.trim().toString()

            if (title.isEmpty() || description.isEmpty()) {
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
            }
        }
    }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}