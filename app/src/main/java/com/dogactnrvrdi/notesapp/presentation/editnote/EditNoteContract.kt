package com.dogactnrvrdi.notesapp.presentation.editnote

import com.dogactnrvrdi.notesapp.data.model.Note

object EditNoteContract {

    data class UiState(
        val note: Note? = null
    )

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val actionLabel: String?) : UiEffect
        data object NavigateToNotesScreen : UiEffect
        data object NavigateBack : UiEffect
        data object GoBack : UiEffect
    }

    sealed interface UiAction {
        data class GetNote(val noteId: Int) : UiAction
        data class UpdateNote(val note: Note) : UiAction
        data object BackClick : UiAction
        data object GoBack : UiAction
        data class ShowSnackbar(
            val message: String,
            val actionLabel: String?,
        ) : UiAction
    }
}