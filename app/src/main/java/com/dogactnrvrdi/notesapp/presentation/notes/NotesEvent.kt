package com.dogactnrvrdi.notesapp.presentation.notes

import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.domain.model.Note

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    data object RestoreNote : NotesEvent()
    data object ToggleOrderSection : NotesEvent()
}