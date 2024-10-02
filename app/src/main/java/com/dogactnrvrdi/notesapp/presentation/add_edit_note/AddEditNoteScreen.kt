package com.dogactnrvrdi.notesapp.presentation.add_edit_note

import android.annotation.SuppressLint
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.presentation.add_edit_note.components.ColorSection
import com.dogactnrvrdi.notesapp.presentation.add_edit_note.components.DescriptionTextField
import com.dogactnrvrdi.notesapp.presentation.add_edit_note.components.TitleTextField
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {

    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val colorState = viewModel.colorState.value

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {

                is AddEditNoteViewModel.UIEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(event.message.toInt())
                    )
                }

                is AddEditNoteViewModel.UIEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.saveNote()
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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
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
                    viewModel.toggleColorSection()
                }) {
                    Icon(
                        imageVector = Icons.Default.ColorLens,
                        contentDescription = stringResource(R.string.select_color_button),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            AnimatedVisibility(
                visible = colorState.isColorSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                ColorSection(
                    modifier = Modifier
                        .background(colorResource(id = R.color.custom_toolbar_color))
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    viewModel = viewModel,
                    scope = scope,
                    noteBackgroundAnimatable = noteBackgroundAnimatable
                )
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
                text = titleState,
                onValueChange = {
                    viewModel.enteredTitle(it)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionTextField(
                text = contentState,
                onValueChange = {
                    viewModel.enteredContent(it)
                },
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}