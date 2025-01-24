package com.dogactnrvrdi.notesapp.presentation.notedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiAction
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiEffect
import com.dogactnrvrdi.notesapp.presentation.notedetail.NoteDetailContract.UiState
import com.dogactnrvrdi.notesapp.presentation.notedetail.components.DescriptionText
import com.dogactnrvrdi.notesapp.presentation.notedetail.components.NoteDetailScreenTopBar
import com.dogactnrvrdi.notesapp.presentation.notedetail.components.TitleText
import kotlinx.coroutines.flow.Flow

@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteId: Int,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit
) {

    LaunchedEffect(Unit) {
        onAction(UiAction.GetNote(noteId))
    }

    LaunchedEffect(uiEffect) {
        uiEffect.collect { effect ->
            when (effect) {

                UiEffect.NavigateBack -> navController.navigateUp()

                is UiEffect.NavigateToEditNote -> {
                    // Navigate to EditNoteScreen
                }
            }
        }
    }

    uiState.note?.let { note ->
        NoteDetailContent(
            title = note.title,
            description = note.description,
            color = note.color,
            onAction = onAction
        )
    }
}

@Composable
fun NoteDetailContent(
    title: String,
    description: String,
    color: Int,
    onAction: (UiAction) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            NoteDetailScreenTopBar(
                onEditClick = { onAction(UiAction.EditNoteClick) },
                onBackClick = { onAction(UiAction.BackClick) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {

            Box(
                modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth()
                    .background(Color(color))
            )

            Spacer(modifier = Modifier.height(10.dp))

            TitleText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = title
            )

            Spacer(modifier = Modifier.height(16.dp))

            DescriptionText(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = description
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NoteDetailContentPreview() {
    NoteDetailContent(
        title = "This Is An Example Note Title",
        description = "This is an example note description.",
        color = 0xFFE57373.toInt(),
        onAction = {}
    )
}