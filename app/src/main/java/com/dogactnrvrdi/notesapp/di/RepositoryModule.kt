package com.dogactnrvrdi.notesapp.di

import com.dogactnrvrdi.notesapp.data.repo.NoteRepositoryImpl
import com.dogactnrvrdi.notesapp.domain.repo.NoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNoteRepository(repositoryImpl: NoteRepositoryImpl): NoteRepository
}