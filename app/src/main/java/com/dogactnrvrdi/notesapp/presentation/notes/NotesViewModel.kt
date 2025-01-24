package com.dogactnrvrdi.notesapp.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
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

            is UiAction.Order -> order(uiAction.noteOrder)

            is UiAction.FabClick -> {
                viewModelScope.launch {
                    emitUiEffect(
                        UiEffect.NavigateToAddNoteScreen
                    )
                }
            }

            is UiAction.NoteClick -> {
                viewModelScope.launch {
                    emitUiEffect(
                        UiEffect.NavigateToNoteDetailScreen(uiAction.noteId)
                    )
                }
            }

            is UiAction.GetNotes -> getNotes(uiAction.noteOrder)

            is UiAction.DeleteNote -> deleteNote(uiAction.note)

            is UiAction.RestoreNote -> {
                restoreNote()
                viewModelScope.launch {
                    emitUiEffect(
                        UiEffect.ShowSnackbar(
                            message = uiAction.message,
                            actionLabel = null
                        )
                    )
                }
            }

            is UiAction.SearchNote -> {
                searchNote(query = uiAction.query, noteOrder = uiAction.noteOrder)
            }

            is UiAction.ShowSnackbar -> {
                viewModelScope.launch {
                    emitUiEffect(
                        UiEffect.ShowSnackbar(
                            message = uiAction.message,
                            actionLabel = uiAction.actionLabel
                        )
                    )
                }
            }
        }
    }

    private fun getNotes(noteOrder: NoteOrder) {
        viewModelScope.launch {
            noteUseCases.getNotes(noteOrder).onStart {
                updateUiState { copy(isLoading = true) }
            }.onCompletion {
                updateUiState { copy(isLoading = false) }
            }.collect { notes ->
                updateUiState { copy(notes = notes, noteOrder = noteOrder, isLoading = false) }
            }
        }
    }

    private fun searchNote(
        query: String,
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ) {
        viewModelScope.launch {
            noteUseCases.searchNote("%$query%", noteOrder).collect { notes ->
                updateUiState { copy(notes = notes, noteOrder = noteOrder) }
            }
        }
    }

    private fun restoreNote() {
        viewModelScope.launch {
            noteUseCases.addNote(uiState.value.recentlyDeletedNote ?: return@launch)
            updateUiState { copy(recentlyDeletedNote = null) }
        }
    }

    private fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.deleteNote(note)
            updateUiState { copy(recentlyDeletedNote = note) }
        }
    }

    private fun order(noteOrder: NoteOrder) {
        if (uiState.value.noteOrder::class == noteOrder::class &&
            uiState.value.noteOrder.orderType == noteOrder.orderType
        ) {
            return
        }
        getNotes(noteOrder)
    }
}