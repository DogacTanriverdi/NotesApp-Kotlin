package com.dogactnrvrdi.notesapp.presentation.addeditnote

import com.dogactnrvrdi.notesapp.data.model.Note

object AddEditNoteContract {

    data class UiState(
        val currentNoteId: Int? = null,
        val note: Note? = null
    )

    sealed interface UiEffect {
        data object NavigateBack: UiEffect
    }

    sealed interface UiAction {
        data class SaveNote(val note: Note): UiAction
        data class GetNote(val noteId: Int): UiAction
        data object BackClick: UiAction
    }
}