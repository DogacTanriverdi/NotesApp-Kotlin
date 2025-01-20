package com.dogactnrvrdi.notesapp.di

import com.dogactnrvrdi.notesapp.data.repo.NoteRepositoryImpl
import com.dogactnrvrdi.notesapp.data.source.local.NoteDao
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import com.dogactnrvrdi.notesapp.domain.use_case.AddNoteUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.DeleteNoteUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.GetNoteUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.GetNotesUseCase
import com.dogactnrvrdi.notesapp.domain.use_case.NoteUseCases
import com.dogactnrvrdi.notesapp.domain.use_case.SearchNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteRepository(dao: NoteDao): NoteRepository =
        NoteRepositoryImpl(dao)

    @Singleton
    @Provides
    fun provideNoteUseCases(
        repo: NoteRepository
    ): NoteUseCases =
        NoteUseCases(
            getNotes = GetNotesUseCase(repo),
            deleteNote = DeleteNoteUseCase(repo),
            addNote = AddNoteUseCase(repo),
            getNote = GetNoteUseCase(repo),
            searchNote = SearchNoteUseCase(repo)
        )
}