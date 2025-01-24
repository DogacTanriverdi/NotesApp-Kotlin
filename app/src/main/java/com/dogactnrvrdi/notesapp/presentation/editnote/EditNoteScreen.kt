package com.dogactnrvrdi.notesapp.presentation.editnote

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.presentation.components.ColorSection
import com.dogactnrvrdi.notesapp.presentation.components.CustomFab
import com.dogactnrvrdi.notesapp.presentation.components.DescriptionTextField
import com.dogactnrvrdi.notesapp.presentation.components.TitleTextField
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteContract.UiState
import com.dogactnrvrdi.notesapp.presentation.editnote.components.EditNoteScreenTopBar
import com.dogactnrvrdi.notesapp.presentation.editnote.components.NavigateBackDialog
import com.dogactnrvrdi.notesapp.presentation.navigation.Screen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun EditNoteScreen(
    navController: NavController,
    noteId: Int,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var isNavigateBackDialogVisible by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onAction(UiAction.GetNote(noteId))
    }

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {

                is UiEffect.ShowSnackbar -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            actionLabel = effect.actionLabel
                        )
                    }
                }

                UiEffect.NavigateToNotesScreen -> navController.navigate(Screen.NotesScreen)

                UiEffect.NavigateBack -> isNavigateBackDialogVisible = true

                UiEffect.GoBack -> navController.navigateUp()
            }
        }
    }

    uiState.note?.let { note ->
        EditNoteContent(
            isDialogVisible = isNavigateBackDialogVisible,
            snackbarHostState = snackbarHostState,
            noteId = note.id,
            noteTitle = note.title,
            noteDescription = note.description,
            noteColor = note.color,
            onAction = onAction
        )
    }
}

@Composable
fun EditNoteContent(
    isDialogVisible: Boolean,
    noteId: Int?,
    noteTitle: String,
    noteDescription: String,
    noteColor: Int,
    snackbarHostState: SnackbarHostState,
    onAction: (UiAction) -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    var isNavigateBackDialogVisible by remember { mutableStateOf(isDialogVisible) }

    var color by remember { mutableIntStateOf(noteColor) }
    var isColorSectionVisible by remember { mutableStateOf(false) }

    val noteBackgroundAnimatable = remember { Animatable(Color(color)) }

    var title by remember { mutableStateOf(noteTitle) }
    var description by remember { mutableStateOf(noteDescription) }

    BackHandler {
        if (noteTitle != title || noteDescription != description || noteColor != color) {
            isNavigateBackDialogVisible = true
        } else {
            onAction(UiAction.GoBack)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = {
            EditNoteScreenTopBar(
                onColorClick = { isColorSectionVisible = !isColorSectionVisible },
                onBackClick = {
                    if (noteTitle != title || noteDescription != description || noteColor != color) {
                        isNavigateBackDialogVisible = true
                    } else {
                        onAction(UiAction.GoBack)
                    }
                }
            )
        },
        floatingActionButton = {
            CustomFab(
                icon = Icons.Default.Save,
                contentDescription = stringResource(R.string.save_note_button),
            ) {
                if (title.isBlank() && description.isBlank()) {
                    onAction(
                        UiAction.ShowSnackbar(
                            message = context.getString(R.string.title_or_description_cannot_be_empty),
                            actionLabel = null
                        )
                    )
                    return@CustomFab
                }

                onAction(
                    UiAction.UpdateNote(
                        note = Note(
                            id = noteId,
                            title = title,
                            description = description,
                            color = color
                        )
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .imePadding()
        ) {

            AnimatedVisibility(
                visible = isColorSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                ColorSection(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    scope = coroutineScope,
                    noteColor = color,
                    noteBackgroundAnimatable = noteBackgroundAnimatable
                ) { selectedColor ->
                    color = selectedColor
                }
            }

            Box(
                modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth()
                    .background(noteBackgroundAnimatable.value)
            )

            Spacer(modifier = Modifier.height(10.dp))

            TitleTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                value = title,
                onValueChange = { title = it },
                hint = stringResource(R.string.title)
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                value = description,
                onValueChange = { description = it },
                hint = stringResource(R.string.description)
            )
        }

        if (isNavigateBackDialogVisible) {
            NavigateBackDialog(
                onDismissRequest = {
                    isNavigateBackDialogVisible = false
                },
                onGoBackClick = {
                    onAction(UiAction.GoBack)
                    isNavigateBackDialogVisible = false
                },
                onStayClick = {
                    isNavigateBackDialogVisible = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EditNoteContentPreview() {
    EditNoteContent(
        isDialogVisible = true,
        noteId = 1,
        noteTitle = "Note Title",
        noteDescription = "Note description",
        noteColor = 0xFFE57373.toInt(),
        snackbarHostState = SnackbarHostState(),
        onAction = {}
    )
}