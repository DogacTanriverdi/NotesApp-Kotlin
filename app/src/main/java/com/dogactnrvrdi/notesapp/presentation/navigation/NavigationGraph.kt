package com.dogactnrvrdi.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteScreen
import com.dogactnrvrdi.notesapp.presentation.addnote.AddNoteViewModel
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteScreen
import com.dogactnrvrdi.notesapp.presentation.editnote.EditNoteViewModel
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailScreen
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailViewModel
import com.dogactnrvrdi.notesapp.presentation.notes.NotesScreen
import com.dogactnrvrdi.notesapp.presentation.notes.NotesViewModel

@Composable
fun NavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = Modifier.then(modifier),
        navController = navController,
        startDestination = Screen.NotesScreen
    ) {

        composable<Screen.NotesScreen> {
            val viewModel: NotesViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            NotesScreen(
                navController = navController,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = onAction
            )
        }

        composable<Screen.AddNoteScreen> {
            val viewModel: AddNoteViewModel = hiltViewModel()
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            AddNoteScreen(
                navController = navController,
                uiEffect = uiEffect,
                onAction = onAction
            )
        }

        composable<Screen.NoteDetailScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Screen.NoteDetailScreen>()
            val viewModel: NoteDetailViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            NoteDetailScreen(
                navController = navController,
                noteId = args.noteId,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = onAction
            )
        }

        composable<Screen.EditNoteScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Screen.EditNoteScreen>()
            val viewModel: EditNoteViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            EditNoteScreen(
                navController = navController,
                noteId = args.noteId,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = onAction
            )
        }
    }
}