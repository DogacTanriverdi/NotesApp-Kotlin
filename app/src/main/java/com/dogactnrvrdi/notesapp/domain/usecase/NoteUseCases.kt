package com.dogactnrvrdi.notesapp.domain.usecase

data class NoteUseCases(
    val getNotes: GetNotesUseCase,
    val deleteNote: DeleteNoteUseCase,
    val addNote: AddNoteUseCase,
    val updateNote: UpdateNoteUseCase,
    val getNote: GetNoteUseCase,
    val searchNote: SearchNoteUseCase
)
