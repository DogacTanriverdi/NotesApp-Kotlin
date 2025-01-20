package com.dogactnrvrdi.notesapp.data.repo

import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.data.source.local.NoteDao
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val dao: NoteDao
) : NoteRepository {

    override fun getNotes(): Flow<List<Note>> =
        dao.getNotes()

    override suspend fun getNoteById(id: Int): Note? =
        dao.getNoteById(id)

    override suspend fun insertNote(note: Note) =
        dao.insertNote(note)

    override suspend fun deleteNote(note: Note) =
        dao.deleteNote(note)

    override fun searchNote(query: String): Flow<List<Note>> =
        dao.searchNote(query)
}