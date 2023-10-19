package com.dogactnrvrdi.notesapp.domain

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.notesapp.data.model.Note

interface NoteRepository {

    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
    fun getAllNotesSortedByCreated(): LiveData<List<Note>>
    fun searchNote(query: String): LiveData<List<Note>>
}