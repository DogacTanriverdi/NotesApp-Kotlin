package com.dogactnrvrdi.notesapp.repo

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.notesapp.db.INoteDao
import com.dogactnrvrdi.notesapp.model.Note

class NoteRepository(
    private val noteDao: INoteDao
) {
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun deleteNote(note: Note) = noteDao.deleteNote(note)

    fun getAllNotesSortedByCreated(): LiveData<List<Note>> = noteDao.getAllNotesSortedByCreated()
}