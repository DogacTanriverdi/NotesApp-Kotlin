package com.dogactnrvrdi.notesapp.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect = _uiEffect.receiveAsFlow()

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.GetNote -> getNote(uiAction.noteId)

            UiAction.BackClick -> {
                viewModelScope.launch {
                    emitUiEffect(UiEffect.NavigateBack)
                }
            }

            UiAction.EditNoteClick -> {
                viewModelScope.launch {
                    uiState.value.note?.let { note ->
                        emitUiEffect(UiEffect.NavigateToEditNote(note))
                    }
                }
            }
        }
    }

    private fun getNote(noteId: Int) {
        if (noteId != -1) {
            viewModelScope.launch {
                val note = noteUseCases.getNote(noteId)
                updateUiState { copy(note = note) }
            }
        }
    }
}