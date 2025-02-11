package com.dogactnrvrdi.notesapp.di

import com.dogactnrvrdi.notesapp.domain.usecase.AddNoteUseCase
import com.dogactnrvrdi.notesapp.domain.usecase.DeleteNoteUseCase
import com.dogactnrvrdi.notesapp.domain.usecase.GetNoteUseCase
import com.dogactnrvrdi.notesapp.domain.usecase.GetNotesUseCase
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import com.dogactnrvrdi.notesapp.domain.usecase.SearchNoteUseCase
import com.dogactnrvrdi.notesapp.domain.usecase.UpdateNoteUseCase
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
        addNoteUseCase: AddNoteUseCase,
        updateNoteUseCase: UpdateNoteUseCase,
        getNoteUseCase: GetNoteUseCase,
        searchNoteUseCase: SearchNoteUseCase
    ): NoteUseCases {
        return NoteUseCases(
            getNotes = getNotesUseCase,
            deleteNote = deleteNoteUseCase,
            addNote = addNoteUseCase,
            updateNote = updateNoteUseCase,
            getNote = getNoteUseCase,
            searchNote = searchNoteUseCase
        )
    }
}