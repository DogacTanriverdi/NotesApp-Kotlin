package com.dogactnrvrdi.notesapp.presentation.add_edit_note.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.dogactnrvrdi.notesapp.data.model.Note
import com.dogactnrvrdi.notesapp.presentation.add_edit_note.AddEditNoteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ColorSection(
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel,
    scope: CoroutineScope,
    noteBackgroundAnimatable: Animatable<Color, AnimationVector4D>
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Note.noteColors.forEach { color ->
            val colorInt = color.toArgb()

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shadow(15.dp, RectangleShape)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color)
                    .border(
                        width = 3.dp,
                        color = if (viewModel.noteColor.value == colorInt)
                            MaterialTheme.colorScheme.onBackground
                        else
                            Color.Transparent,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        scope.launch {
                            noteBackgroundAnimatable.animateTo(
                                targetValue = Color(colorInt),
                                animationSpec = tween(durationMillis = 500)
                            )
                        }
                        viewModel.changeColor(colorInt)
                    }
            )
        }
    }
}