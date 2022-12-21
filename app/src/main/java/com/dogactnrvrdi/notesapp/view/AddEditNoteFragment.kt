package com.dogactnrvrdi.notesapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.databinding.FragmentAddEditNoteBinding

class AddEditNoteFragment : Fragment(R.layout.fragment_add_edit_note) {

    // Binding
    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Binding
        _binding = FragmentAddEditNoteBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}