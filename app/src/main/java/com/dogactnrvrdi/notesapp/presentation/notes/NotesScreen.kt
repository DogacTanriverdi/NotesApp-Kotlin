package com.dogactnrvrdi.notesapp.presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.common.NoteOrder
import com.dogactnrvrdi.notesapp.common.OrderType
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.presentation.components.CustomFab
import com.dogactnrvrdi.notesapp.presentation.navigation.Screen
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.notes.NotesContract.UiState
import com.dogactnrvrdi.notesapp.presentation.notes.components.NoteItem
import com.dogactnrvrdi.notesapp.presentation.notes.components.NotesScreenTopBar
import com.dogactnrvrdi.notesapp.presentation.notes.components.OrderSection
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun NotesScreen(
    navController: NavController,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        onAction(UiAction.GetNotes(noteOrder = uiState.noteOrder))
    }

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {

                is UiEffect.ShowSnackbar -> {
                    coroutineScope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = effect.message,
                            actionLabel = effect.actionLabel
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            onAction(UiAction.RestoreNote(message = context.getString(R.string.note_restored_successfully)))
                        }
                    }
                }

                is UiEffect.NavigateToAddEditNoteScreen -> {
                    navController.navigate(
                        route = Screen.AddNoteScreen
                    )
                }
            }
        }
    }

    NotesContent(
        snackbarHostState = snackbarHostState,
        notes = uiState.notes,
        noteOrder = uiState.noteOrder,
        onAction = onAction,
    )
}

@Composable
fun NotesContent(
    snackbarHostState: SnackbarHostState,
    notes: List<Note>,
    noteOrder: NoteOrder,
    onAction: (UiAction) -> Unit
) {
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

    var isSearchSectionVisible by rememberSaveable { mutableStateOf(false) }
    var isOrderSectionVisible by rememberSaveable { mutableStateOf(false) }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchSectionVisible) {
        if (isSearchSectionVisible) {
            focusRequester.requestFocus()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            NotesScreenTopBar(
                onSearchClick = { isSearchSectionVisible = !isSearchSectionVisible },
                onSortClick = { isOrderSectionVisible = !isOrderSectionVisible }
            )
        },
        floatingActionButton = {
            CustomFab(
                icon = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_new_note_button),
            ) {
                onAction(UiAction.FabClick())
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            AnimatedVisibility(
                visible = isSearchSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxWidth()
                            .padding(10.dp)
                            .focusRequester(focusRequester),
                        shape = RoundedCornerShape(10.dp),
                        label = {
                            Text(text = stringResource(id = R.string.search))
                        },
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            onAction(UiAction.SearchNote(query = searchQuery))
                        }
                    )
                }
            }

            AnimatedVisibility(
                visible = isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surface)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = noteOrder,
                    onOrderChange = { noteOrder ->
                        onAction(UiAction.Order(noteOrder = noteOrder))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(160.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(notes) { note ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        NoteItem(
                            note = note,
                            modifier = Modifier.clickable {
                                onAction(
                                    UiAction.FabClick(
                                        noteId = note.id ?: -1,
                                        noteColor = note.color
                                    )
                                )
                            },
                            onDeleteClick = {
                                onAction(UiAction.DeleteNote(note = note))
                                onAction(
                                    UiAction.ShowSnackbar(
                                        message = context.getString(R.string.note_deleted_successfully),
                                        actionLabel = context.getString(R.string.undo)
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NotesContentPreview() {
    NotesContent(
        snackbarHostState = SnackbarHostState(),
        notes = emptyList(),
        noteOrder = NoteOrder.Date(OrderType.Descending),
        onAction = {}
    )
}