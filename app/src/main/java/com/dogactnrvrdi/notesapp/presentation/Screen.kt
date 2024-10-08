package com.dogactnrvrdi.notesapp.presentation

sealed class Screen(val route: String) {
    data object NotesScreen : Screen("notes_screen")
    data object AddEditNoteScreen : Screen("add_edit_note_screen")
}