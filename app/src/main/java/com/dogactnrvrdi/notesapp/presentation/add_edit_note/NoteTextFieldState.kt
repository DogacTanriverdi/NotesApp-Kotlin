package com.dogactnrvrdi.notesapp.presentation.add_edit_note

data class NoteTextFieldState(
    val text: String = "",
    val hint: Int = -1,
    val isHintVisible: Boolean = false
)
