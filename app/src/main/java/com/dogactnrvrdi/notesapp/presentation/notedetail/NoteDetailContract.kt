package com.dogactnrvrdi.notesapp.presentation.notedetail

import com.dogactnrvrdi.notesapp.data.model.Note

object NoteDetailContract {

    data class UiState(
        val note: Note? = null
    )

    sealed interface UiEffect {
        data object NavigateBack : UiEffect
        data class NavigateToEditNote(val note: Note) : UiEffect
    }

    sealed interface UiAction {
        data class GetNote(val noteId: Int) : UiAction
        data object BackClick : UiAction
        data object EditNoteClick : UiAction
    }
}