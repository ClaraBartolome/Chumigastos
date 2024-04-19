package com.example.myapplication.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.common.NUMBER_MAX_CHARACTERS_NAME
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun CustomTextField(
    text: MutableState<String> = remember { mutableStateOf("") },
    maxChar:Int = NUMBER_MAX_CHARACTERS_NAME,
    placeholder: String = "placeholder",
    label: String = "label",
) {
    TextField(
        // The `menuAnchor` modifier must be passed to the text field for correctness.
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        value = text.value,
        onValueChange = {
            if(it.length <= maxChar){
                text.value = it
            }
                        },
        placeholder = {
            Text(
                text = placeholder,
                color = MaterialTheme.colorScheme.primary.copy(0.5f),
            )
        },
        label = { Text(label) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            errorContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            disabledSupportingTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            errorLabelColor = MaterialTheme.colorScheme.primary,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            disabledIndicatorColor = MaterialTheme.colorScheme.primary,
            errorIndicatorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
            CustomTextField()
        }
    }
}