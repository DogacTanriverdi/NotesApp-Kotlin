package com.dogactnrvrdi.notesapp.di

import android.content.Context
import androidx.room.Room
import com.dogactnrvrdi.notesapp.data.repo.NoteRepositoryImpl
import com.dogactnrvrdi.notesapp.data.source.local.NoteDao
import com.dogactnrvrdi.notesapp.data.source.local.NoteDatabase
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import com.dogactnrvrdi.notesapp.domain.use_case.AddNoteUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.DeleteNoteUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.GetNotesUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        NoteDatabase::class.java,
        "noteDB"
    ).build()

    @Singleton
    @Provides
    fun provideNoteDao(db: NoteDatabase) =
        db.getNoteDao()

    @Singleton
    @Provides
    fun provideNoteRepository(dao: NoteDao): NoteRepository =
        NoteRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideNoteUseCases(repo: NoteRepository): NoteUseCases =
        NoteUseCases(
            getNotes = GetNotesUseCase(repo),
            deleteNote = DeleteNoteUseCase(repo),
            addNote = AddNoteUseCase(repo)
        )
}