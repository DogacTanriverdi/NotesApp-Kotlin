package com.dogactnrvrdi.notesapp.presentation.notes

import android.annotation.SuppressLint
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.R
import com.dogactnrvrdi.notesapp.presentation.Screen
import com.dogactnrvrdi.notesapp.presentation.notes.components.NoteItem
import com.dogactnrvrdi.notesapp.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState =  snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.add_new_note_button)
                )
            }
        },
        modifier = Modifier
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
                    .background(colorResource(id = R.color.custom_toolbar_color))
                    .height(70.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = stringResource(id = R.string.notes),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(start = 15.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(id = R.color.custom_toolbar_color))
                        .height(70.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(
                        onClick = {
                            viewModel.searchSection()
                        }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(id = R.string.search_button),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    IconButton(
                        onClick = {
                            viewModel.toggleOrderSection()
                        },
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Sort,
                            contentDescription = stringResource(id = R.string.sort_button),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = state.isSearchSectionVisible,
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
                            viewModel.searchNote(searchQuery)
                        }
                    )
                }
            }

            LaunchedEffect(state.isSearchSectionVisible) {
                if (state.isSearchSectionVisible) {
                    focusRequester.requestFocus()
                }
            }

            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .background(colorResource(id = R.color.custom_toolbar_color))
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = { noteOrder ->
                        viewModel.order(noteOrder)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.notes) { note ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                            NoteItem(
                                note = note,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(
                                            route = Screen.AddEditNoteScreen.route +
                                                    "?noteId=${note.id}&noteColor=${note.color}"
                                        )
                                    },
                                onDeleteClick = {
                                    viewModel.deleteNote(note)
                                    scope.launch {

                                        val result = snackbarHostState.showSnackbar(
                                            message = context.getString(R.string.note_deleted_successfully),
                                            actionLabel = context.getString(R.string.undo)
                                        )

                                        if (result == SnackbarResult.ActionPerformed) {
                                            viewModel.restoreNote()
                                        }
                                    }
                                }
                            )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}