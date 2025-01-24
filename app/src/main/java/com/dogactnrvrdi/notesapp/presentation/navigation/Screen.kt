package com.dogactnrvrdi.notesapp.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data object NotesScreen : Screen

    @Serializable
    data object AddNoteScreen : Screen

    @Serializable
    data class NoteDetailScreen(val noteId: Int) : Screen

    @Serializable
    data class AddEditNoteScreen(val noteId: Int = -1, val noteColor: Int = -1) : Screen
}