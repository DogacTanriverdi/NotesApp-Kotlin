package com.dogactnrvrdi.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteScreen
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteViewModel
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

        composable<Screen.AddEditNoteScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Screen.AddEditNoteScreen>()
            val viewModel: AddEditNoteViewModel = hiltViewModel()
            val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
            val uiEffect = viewModel.uiEffect
            val onAction = viewModel::onAction

            AddEditNoteScreen(
                navController = navController,
                noteId = args.noteId,
                noteColor = args.noteColor,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = onAction
            )
        }
    }
}