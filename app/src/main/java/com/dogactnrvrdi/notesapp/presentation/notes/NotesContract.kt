package com.dogactnrvrdi.notesapp.presentation.notes

import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType
import com.dogactnrvrdi.notesapp.data.model.Note

object NotesContract {

    data class UiState(
        val notes: List<Note> = emptyList(),
        val isLoading: Boolean = false,
        val recentlyDeletedNote: Note? = null,
        val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    )

    sealed interface UiEffect {
        data class ShowSnackbar(val message: String, val actionLabel: String?) : UiEffect
        data object NavigateToAddNoteScreen : UiEffect
        data class NavigateToNoteDetailScreen(val noteId: Int) : UiEffect
    }

    sealed interface UiAction {
        data class Order(val noteOrder: NoteOrder) : UiAction
        data object FabClick : UiAction
        data class NoteClick(val noteId: Int) : UiAction
        data class GetNotes(val noteOrder: NoteOrder) : UiAction
        data class DeleteNote(val note: Note) : UiAction
        data class RestoreNote(val message: String) : UiAction
        data class SearchNote(
            val query: String,
            val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
        ) : UiAction

        data class ShowSnackbar(
            val message: String,
            val actionLabel: String,
        ) : UiAction
    }
}