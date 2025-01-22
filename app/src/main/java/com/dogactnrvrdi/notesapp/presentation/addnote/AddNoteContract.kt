package com.dogactnrvrdi.notesapp.presentation.addnote

import com.dogactnrvrdi.notesapp.data.model.Note

object AddNoteContract {

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val actionLabel: String?) : UiEffect
        data object NavigateBack: UiEffect
    }

    sealed interface UiAction {
        data class SaveNote(val note: Note): UiAction
        data object BackClick: UiAction
        data class ShowSnackbar(
            val message: String,
            val actionLabel: String?,
        ) : UiAction
    }
}