package com.dogactnrvrdi.notesapp.presentation.addnote

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
import androidx.compose.foundation.layout.WindowInsets
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
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.addnote.components.AddNoteScreenTopBar
import com.dogactnrvrdi.notesapp.presentation.addnote.components.ColorSection
import com.dogactnrvrdi.notesapp.presentation.addnote.components.DescriptionTextField
import com.dogactnrvrdi.notesapp.presentation.addnote.components.TitleTextField
import com.dogactnrvrdi.notesapp.presentation.components.CustomFab
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun AddNoteScreen(
    navController: NavController,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val coroutineScope = rememberCoroutineScope()

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

                is UiEffect.NavigateBack -> navController.navigateUp()
            }
        }
    }

    AddNoteContent(
        onAction = onAction,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun AddNoteContent(
    onAction: (UiAction) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    var isColorSectionVisible by remember { mutableStateOf(false) }

    var color by remember { mutableIntStateOf(Note.getRandomColor()) }

    val noteBackgroundAnimatable = remember { Animatable(Color(color)) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AddNoteScreenTopBar(
                onColorClick = { isColorSectionVisible = !isColorSectionVisible },
                onBackClick = { onAction(UiAction.BackClick) }
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
                    UiAction.SaveNote(
                        note = Note(
                            title = title,
                            description = description,
                            color = color
                        )
                    )
                )
            }
        },
        contentWindowInsets = WindowInsets(bottom = 0)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .imePadding(),
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
    }
}

@Preview(showBackground = true)
@Composable
private fun AddNoteContentPreview() {
    AddNoteContent(
        onAction = {},
        snackbarHostState = SnackbarHostState()
    )
}