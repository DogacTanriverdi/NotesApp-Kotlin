package com.dogactnrvrdi.notesapp.di

import android.content.Context
import androidx.room.Room
import com.dogactnrvrdi.notesapp.data.source.local.NoteDao
import com.dogactnrvrdi.notesapp.data.source.local.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDatabaseModule {

    @[Provides Singleton]
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ): NoteDatabase {
        return Room.databaseBuilder(
            context,
            NoteDatabase::class.java,
            "noteDB"
        ).build()
    }

    @[Provides Singleton]
    fun provideNoteDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.getNoteDao()
    }
}