package com.dogactnrvrdi.notesapp.domain.use_case

import com.dogactnrvrdi.notesapp.domain.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? =
        repo.getNoteById(id)
}