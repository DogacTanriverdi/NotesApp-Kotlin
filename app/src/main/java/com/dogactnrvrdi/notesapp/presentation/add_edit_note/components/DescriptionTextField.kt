package com.dogactnrvrdi.notesapp.presentation.add_edit_note.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun DescriptionTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isHintVisible: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {

        BasicTextField(
            value = text,
            onValueChange = { newValue ->
                onValueChange(capitalizeFirstLetter(newValue))
            },
            singleLine = singleLine,
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions.Default.copy(
                capitalization = KeyboardCapitalization.Sentences
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 10.dp)
                .onFocusChanged {
                    onFocusChange(it)
                },
        )

        if (isHintVisible)
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = hint,
                style = textStyle,
                color = MaterialTheme.colorScheme.onError
            )
    }
}

fun capitalizeFirstLetter(input: String): String {
    return if (input.isNotEmpty()) {
        input.replaceFirstChar { it.uppercaseChar() }
    } else {
        input
    }
}