package com.dogactnrvrdi.notesapp.presentation.editnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.GetNote -> getNote(uiAction.noteId)

            is UiAction.UpdateNote -> updateNote(uiAction.note)

            UiAction.BackClick -> {
                viewModelScope.launch {
                    emitUiEffect(UiEffect.NavigateBack)
                }
            }

            UiAction.GoBack -> {
                viewModelScope.launch {
                    emitUiEffect(UiEffect.GoBack)
                }
            }

            is UiAction.ShowSnackbar -> {
                viewModelScope.launch {
                    emitUiEffect(UiEffect.ShowSnackbar(uiAction.message, uiAction.actionLabel))
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

    private fun updateNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.updateNote(note)
            emitUiEffect(UiEffect.NavigateToNotesScreen)
        }
    }
}