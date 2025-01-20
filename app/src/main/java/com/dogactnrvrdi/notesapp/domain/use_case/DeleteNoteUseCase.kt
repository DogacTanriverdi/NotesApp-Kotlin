package com.dogactnrvrdi.notesapp.domain.use_case

import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    suspend operator fun invoke(note: Note) =
        repo.deleteNote(note)
}