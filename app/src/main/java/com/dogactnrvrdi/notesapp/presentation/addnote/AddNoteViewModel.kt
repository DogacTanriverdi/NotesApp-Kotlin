package com.dogactnrvrdi.notesapp.presentation.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteContract.UiEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
) : ViewModel() {

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect by lazy { _uiEffect.receiveAsFlow() }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {

            is UiAction.SaveNote -> saveNote(uiAction.note)

            UiAction.BackClick -> {
                viewModelScope.launch { emitUiEffect(UiEffect.NavigateBack) }
            }

            is UiAction.ShowSnackbar -> {
                viewModelScope.launch {
                    emitUiEffect(UiEffect.ShowSnackbar(uiAction.message, uiAction.actionLabel))
                }
            }
        }
    }

    private fun saveNote(note: Note) {
        viewModelScope.launch {
            noteUseCases.addNote(note)
            emitUiEffect(UiEffect.NavigateBack)
        }
    }
}