package com.dogactnrvrdi.notesapp.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.domain.model.InvalidNoteException
import com.dogactnrvrdi.notesapp.domain.model.Note
import com.dogactnrvrdi.notesapp.domain.use_case.NoteUseCases
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

    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = R.string.title))
    val noteTitle: State<NoteTextFieldState> get() = _noteTitle

    private val _noteContent = mutableStateOf(NoteTextFieldState(hint = R.string.type_something))
    val noteContent: State<NoteTextFieldState> get() = _noteContent

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
                        currentNoteId = note.id

                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )

                        _noteContent.value = noteContent.value.copy(
                            text = note.description,
                            isHintVisible = false
                        )

                        _noteColor.intValue = note.color
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
                        title = noteTitle.value.text,
                        description = noteContent.value.text,
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
        _noteTitle.value = noteTitle.value.copy(text = value)
    }

    fun changeTitleFocus(focusState: FocusState) {
        _noteTitle.value = noteTitle.value.copy(
            isHintVisible = !focusState.isFocused && noteTitle.value.text.isBlank()
        )
    }

    fun enteredContent(value: String) {
        _noteContent.value = _noteContent.value.copy(text = value)
    }

    fun changeContentFocus(focusState: FocusState) {
        _noteContent.value = _noteContent.value.copy(
            isHintVisible = !focusState.isFocused && _noteContent.value.text.isBlank()
        )
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