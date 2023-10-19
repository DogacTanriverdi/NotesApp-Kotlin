package com.dogactnrvrdi.notesapp.ui.add_edit_note

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.FragmentAddEditNoteBinding
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.common.Util
import com.dogactnrvrdi.notesapp.ui.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NoteViewModel by viewModels()

    private val args: AddEditNoteFragmentArgs by navArgs()

    private var currentNote: Note? = null

    private var isSaved: Boolean = false

    private val util = Util()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddEditNoteBinding.bind(view)

        with(binding) {

            currentNote = args.note

            currentNote?.let { note ->
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

            fabSave.setOnClickListener {
                saveNote()
            }

            fabUpdate.setOnClickListener {
                updateNote()
            }

            noteBodyET.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                if ((scrollY > oldScrollY + 12 && fabSave.isExtended) && (scrollY > oldScrollY + 12 && fabUpdate.isExtended)) {
                    fabSave.shrink()
                    fabUpdate.shrink()
                }
                if ((scrollY < oldScrollY - 12 && !fabSave.isExtended) && (scrollY < oldScrollY - 12 && !fabUpdate.isExtended)) {
                    fabSave.extend()
                    fabUpdate.extend()
                }
            }
        }
    }

    private fun saveNote() {
        with(binding) {
            with(viewModel) {

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
                    currentNote!!.apply {
                        this.title = title
                        this.description = description
                        this.created = System.currentTimeMillis()
                    }
                }

                insertNote(newNote)
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
        with(binding) {
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