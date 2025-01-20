package com.dogactnrvrdi.notesapp.domain.usecase

import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.data.model.InvalidNoteException
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import javax.inject.Inject
import kotlin.jvm.Throws

class AddNoteUseCase @Inject constructor(
    private val repo: NoteRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {

        if (note.title.isBlank())
            throw InvalidNoteException(message = R.string.title_cannot_be_empty.toString())

        if (note.description.isBlank())
            throw InvalidNoteException(message = R.string.content_cannot_be_empty.toString())

        repo.insertNote(note)
    }
}