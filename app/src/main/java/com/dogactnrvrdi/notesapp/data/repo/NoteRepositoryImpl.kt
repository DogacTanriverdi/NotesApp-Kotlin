package com.dogactnrvrdi.notesapp.data.repo

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.data.source.local.NoteDao
import com.dogactnrvrdi.notesapp.domain.NoteRepository
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override suspend fun insertNote(note: Note) = dao.insertNote(note)

    override suspend fun deleteNote(note: Note) = dao.deleteNote(note)

    override fun getAllNotesSortedByCreated(): LiveData<List<Note>> =
        dao.getAllNotesSortedByCreated()

    override fun searchNote(query: String) = dao.searchNote(query)
}