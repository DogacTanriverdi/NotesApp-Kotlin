package com.dogactnrvrdi.notesapp.presentation.addeditnote

import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteContract.UiState
import com.dogactnrvrdi.notesapp.presentation.addeditnote.components.ColorSection
import com.dogactnrvrdi.notesapp.presentation.addeditnote.components.DescriptionTextField
import com.dogactnrvrdi.notesapp.presentation.addeditnote.components.TitleTextField
import kotlinx.coroutines.flow.Flow

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteId: Int,
    noteColor: Int,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var isColorSectionVisible by remember { mutableStateOf(false) }

    var color by remember { mutableIntStateOf(if (noteColor != -1) noteColor else Note.getRandomColor()) }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else color)
        )
    }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    uiState.note?.let { note ->
        title = note.title
        description = note.description
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onAction(UiAction.GetNote(noteId))
    }

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {

                UiEffect.NavigateToNotesScreen -> navController.navigateUp()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAction(
                        UiAction.SaveNote(
                            note = Note(
                                id = if (noteId != -1) noteId else null,
                                title = title,
                                description = description,
                                color = color
                            )
                        )
                    )
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = stringResource(R.string.save_note_button)
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = stringResource(id = R.string.edit_note),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(start = 15.dp)
                    )
                }

                IconButton(onClick = {
                    isColorSectionVisible = !isColorSectionVisible
                }) {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = stringResource(R.string.select_color_button),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

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
                    scope = scope,
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
                modifier = Modifier,
                text = title,
                onValueChange = {
                    title = it
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionTextField(
                text = description,
                onValueChange = {
                    description = it
                },
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}