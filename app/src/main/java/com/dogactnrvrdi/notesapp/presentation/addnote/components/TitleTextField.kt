package com.dogactnrvrdi.notesapp.presentation.addnote.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TitleTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
) {
    Box(modifier = modifier) {

        if (value.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 4.dp),
                text = hint,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
            )
        }

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = { newValue ->
                onValueChange(capitalizeEachWord(newValue))
            },
            maxLines = 3,
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                color = MaterialTheme.colorScheme.onBackground
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Words
            ),
        )
    }
}

fun capitalizeEachWord(input: String): String {
    return input.split(" ")
        .joinToString(" ") { it.replaceFirstChar { char -> char.uppercaseChar() } }
}