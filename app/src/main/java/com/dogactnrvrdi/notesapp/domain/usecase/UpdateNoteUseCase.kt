package com.dogactnrvrdi.notesapp.domain.usecase

import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    suspend operator fun invoke(note: Note) =
        repo.updateNote(note)
}