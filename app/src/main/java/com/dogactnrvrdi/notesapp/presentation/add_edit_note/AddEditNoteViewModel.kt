package com.dogactnrvrdi.notesapp.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.data.model.InvalidNoteException
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> get() = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> get() = _noteContent

    private val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> get() = _noteColor

    private val _colorState = mutableStateOf(ColorState())
    val colorState: State<ColorState> = _colorState

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        with(note) {
                            currentNoteId = id
                            _noteTitle.value = title
                            _noteContent.value = description
                            _noteColor.intValue = color
                        }
                    }
                }
            }
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            try {
                noteUseCases.addNote(
                    Note(
                        title = noteTitle.value,
                        description = noteContent.value,
                        created = System.currentTimeMillis(),
                        color = noteColor.value,
                        id = currentNoteId
                    )
                )
                _eventFlow.emit(UIEvent.SaveNote)
            } catch (e: InvalidNoteException) {
                _eventFlow.emit(
                    UIEvent.ShowSnackbar(
                        message = e.message ?: "Unknown error!\nCouldn't save note"
                    )
                )
            }
        }
    }

    fun enteredTitle(value: String) {
        _noteTitle.value = value
    }

    fun enteredContent(value: String) {
        _noteContent.value = value
    }

    fun changeColor(color: Int) {
        _noteColor.intValue = color
    }

    fun toggleColorSection() {
        _colorState.value = colorState.value.copy(
            isColorSectionVisible = !colorState.value.isColorSectionVisible
        )
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String) : UIEvent()
        data object SaveNote : UIEvent()
    }

    data class ColorState(
        val isColorSectionVisible: Boolean = false
    )
}