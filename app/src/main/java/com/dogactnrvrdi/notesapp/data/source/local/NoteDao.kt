package com.dogactnrvrdi.notesapp.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dogactnrvrdi.notesapp.domain.model.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY created DESC")
    fun getAllNotesSortedByCreated(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE title LIKE :query OR description LIKE :query")
    fun searchNote(query: String?): LiveData<List<Note>>
}