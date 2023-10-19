package com.dogactnrvrdi.notesapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dogactnrvrdi.notesapp.data.model.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao(): NoteDao
}