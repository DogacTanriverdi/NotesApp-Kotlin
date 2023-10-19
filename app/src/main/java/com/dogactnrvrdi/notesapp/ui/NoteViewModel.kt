package com.dogactnrvrdi.notesapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.data.repo.NoteRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repo: NoteRepositoryImpl
) : ViewModel() {

    val notes = repo.getAllNotesSortedByCreated()

    fun insertNote(note: Note) = viewModelScope.launch { repo.insertNote(note) }

    fun deleteNote(note: Note) = viewModelScope.launch { repo.deleteNote(note) }

    fun searchNote(query: String) = repo.searchNote(query)
}