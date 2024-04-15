package com.example.myapplication.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.categories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(selectedText: MutableState<Int>, categoryName: MutableState<String>) {
    var expanded by remember { mutableStateOf(false) }
    var categorySelected by remember{ mutableStateOf(-1) }
    var isCategorySelected by remember{ mutableStateOf(false) }
    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 16.dp),
            readOnly = true,
            value = stringResource(id = selectedText.value),
            onValueChange = {},
            label = { Text(stringResource(id = R.string.category)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                errorContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                focusedTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ))
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = item)) },
                    onClick = {
                        isCategorySelected = true
                        categorySelected = item
                        expanded = false
                    }
                )
            }
        }
    }

    if(isCategorySelected && categorySelected != -1){
        isCategorySelected = false
        selectedText.value = categorySelected
        categoryName.value = stringResource(id = categorySelected)
        categorySelected = -1
    }

}