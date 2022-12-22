package com.dogactnrvrdi.notesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.model.Note
import com.dogactnrvrdi.notesapp.repo.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(
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