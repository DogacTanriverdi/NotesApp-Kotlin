package com.dogactnrvrdi.notesapp.domain.use_case

import com.dogactnrvrdi.notesapp.domain.model.InvalidNoteException
import com.dogactnrvrdi.notesapp.domain.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject
import kotlin.jvm.Throws

class AddNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank()) {
            throw InvalidNoteException("Title cannot be empty!")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("Content cannot be empty!")
        }

        repo.insertNote(note)
    }
}