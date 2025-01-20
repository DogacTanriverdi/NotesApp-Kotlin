package com.dogactnrvrdi.notesapp.di

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
object UseCaseModule {

    @[Provides Singleton]
    fun provideNoteUseCases(
        getNotesUseCase: GetNotesUseCase,
        deleteNoteUseCase: DeleteNoteUseCase,
        addNoteUseCases: AddNoteUseCase,
        getNoteUseCase: GetNoteUseCase,
        searchNoteUseCase: SearchNoteUseCase
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = getNotesUseCase,
            deleteNote = deleteNoteUseCase,
            addNote = addNoteUseCases,
            getNote = getNoteUseCase,
            searchNote = searchNoteUseCase
        )
    }
}