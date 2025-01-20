package com.dogactnrvrdi.notesapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dogactnrvrdi.notesapp.presentation.addeditnote.AddEditNoteScreen
import com.dogactnrvrdi.notesapp.presentation.notes.NotesScreen

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
            NotesScreen(navController = navController)
        }

        composable<Screen.AddEditNoteScreen> { navBackStackEntry ->
            val args = navBackStackEntry.toRoute<Screen.AddEditNoteScreen>()

            AddEditNoteScreen(
                navController = navController,
                noteColor = args.noteColor
            )
        }
    }
}