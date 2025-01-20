package com.dogactnrvrdi.notesapp.domain.repo

import androidx.lifecycle.LiveData
import com.dogactnrvrdi.notesapp.data.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    fun searchNote(query: String): Flow<List<Note>>
}