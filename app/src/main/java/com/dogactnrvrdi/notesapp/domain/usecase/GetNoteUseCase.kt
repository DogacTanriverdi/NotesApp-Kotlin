package com.dogactnrvrdi.notesapp.domain.usecase

import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? =
        repo.getNoteById(id)
}