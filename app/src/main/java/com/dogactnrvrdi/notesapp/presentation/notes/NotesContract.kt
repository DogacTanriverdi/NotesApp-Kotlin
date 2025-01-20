package com.dogactnrvrdi.notesapp.presentation.notes

import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType
import com.dogactnrvrdi.notesapp.data.model.Note

object NotesContract {

    data class UiState(
        val notes: List<Note> = emptyList(),
        val recentlyDeletedNote: Note? = null,
        val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
        val isOrderSectionVisible: Boolean = false
    )

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val actionLabel: String) : UiEffect
        data class NavigateToAddEditNoteScreen(
            val noteId: Int = -1,
            val noteColor: Int = -1
        ) : UiEffect
    }

    sealed interface UiAction {
        data class Order(val noteOrder: NoteOrder) : UiAction
        data object ToggleOrderSection : UiAction
        data class FabClick(
            val noteId: Int = -1,
            val noteColor: Int = -1
        ) : UiAction
        data class GetNotes(val noteOrder: NoteOrder) : UiAction
        data class DeleteNote(val note: Note) : UiAction
        data object RestoreNote : UiAction
        data class SearchNote(
            val query: String,
            val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
        ) : UiAction
        data class ShowSnackbar(
            val message: String,
            val actionLabel: String,
            val onActionPerformed: () -> Unit = {}
        ) : UiAction
    }
}