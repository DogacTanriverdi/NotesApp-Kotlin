package com.dogactnrvrdi.notesapp.view

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.FragmentAddEditNoteBinding
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.util.Util
import com.dogactnrvrdi.notesapp.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text


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

    // Is Saved
    private var isSaved: Boolean = false

    private val util = Util()

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
                val lastModifiedString =
                    getString(R.string.last_modified) + " " +
                            note.createdDateFormatted
                lastModifiedTV.text = lastModifiedString
                lastModifiedTV.visibility = View.VISIBLE
                fabSave.visibility = View.GONE
                fabUpdate.visibility = View.VISIBLE
            }
        }

        // Save Note Fab
        binding.fabSave.setOnClickListener {
            saveNote()
        }

        // Update Note Fab
        binding.fabUpdate.setOnClickListener {
            updateNote()
        }
    }

    private fun saveNote() {
        binding.apply {
            val title = noteTitleET.text?.trim().toString()
            val description = noteBodyET.text?.trim().toString()

            if (title.isEmpty() || description.isEmpty()) {
                util.toastLong(
                    requireContext(),
                    getString(R.string.notes_cannot_be_empty)
                )
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
                util.toastShort(
                    requireContext(),
                    getString(R.string.saved)
                )
                findNavController().popBackStack()
                isSaved = true
            }
        }
    }

    private fun updateNote() {
        binding.apply {
            val title = noteTitleET.text?.trim().toString()
            val description = noteBodyET.text?.trim().toString()

            if (title.isEmpty() || description.isEmpty()) {
                util.toastLong(
                    requireContext(),
                    getString(R.string.notes_cannot_be_empty)
                )
                return
            }

            currentNote?.let {
                if (title == it.title && description == it.description) {
                    util.toastShort(
                        requireContext(),
                        getString(R.string.no_changes)
                    )
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
                util.toastShort(
                    requireContext(),
                    getString(R.string.updated)
                )
                findNavController().popBackStack()
                isSaved = true
            }
        }
    }

    override fun onPause() {
        super.onPause()
        val title = binding.noteTitleET.text?.trim().toString()
        val description = binding.noteBodyET.text?.trim().toString()

        if (title.isEmpty() && description.isEmpty())
            return
        else if (title.isEmpty() || description.isEmpty()) {
            util.toastShort(
                requireContext(),
                getString(R.string.not_saved)
            )
            isSaved = false
        } else {
            if (isSaved) {
                return
            }
            if (title.isEmpty() && description.isEmpty()) {
                return
            } else {
                currentNote?.let {
                    if (title == it.title && description == it.description)
                        return
                    else {
                        util.toastShort(
                            requireContext(),
                            getString(R.string.not_saved)
                        )
                        isSaved = false
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}