package com.dogactnrvrdi.notesapp.presentation.notedetail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    text: String,
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .heightIn(max = MaterialTheme.typography.headlineLarge.fontSize.value.dp * 3 * 1.5f)
    ) {
        BasicText(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState),
            text = text,
            style = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            )
        )
    }
}