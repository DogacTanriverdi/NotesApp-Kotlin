package com.dogactnrvrdi.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.repo.NoteRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

@ViewModelScoped
class NoteViewModel @Inject constructor (
    private val repo: NoteRepository
) : ViewModel() {

    val notes = repo.getAllNotesSortedByCreated()

    fun insertNote(note: Note) = viewModelScope.launch {
        repo.insertNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        repo.deleteNote(note)
    }
}