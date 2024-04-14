package com.example.myapplication.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.common.categories
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertAdd(
    eur: Float,
    yen: Float,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val categoryName = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val selectedText = remember { mutableStateOf(categories[0]) }
    var categorySelected by remember{ mutableStateOf(-1) }
    var isCategorySelected by remember{ mutableStateOf(false) }
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp)
        ) {
            Column(verticalArrangement = Arrangement.SpaceEvenly, horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.add_purchase),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )

                PopUpTextField(text = itemName, placeholder = stringResource(id = R.string.trifle), label = stringResource(id = R.string.trifle_name))
                PopUpTextField(text = storeName, placeholder = stringResource(id = R.string.acme), label = stringResource(id = R.string.store_name))

                ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
                    TextField(
                        // The `menuAnchor` modifier must be passed to the text field for correctness.
                        modifier = Modifier
                            .menuAnchor()
                            .padding(start = 16.dp, top = 8.dp, end = 16.dp),
                        readOnly = true,
                        value = categoryName.value,
                        onValueChange = {},
                        label = { Text(stringResource(id = R.string.category)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
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
                    categoryName.value = stringResource(id = categorySelected)
                    categorySelected = -1
                }

                ShowMoneyExchangeItem(eur = eur, yen = yen)

                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically , modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)){
                    PopUpButton(stringResource(id = R.string.dismiss)){onDismissRequest.invoke()}
                    PopUpButton(stringResource(id = R.string.accept)){onConfirmation.invoke()}
                }
            }
        }
    }

}

@Composable
private fun PopUpTextField(text: MutableState<String>, placeholder: String, label:String){
    TextField(
        value = text.value,
        onValueChange = {},
        placeholder = {
            Text(text = placeholder,
                color = Color.LightGray
            )},
        label = {
            Text(text = label,
                color = MaterialTheme.colorScheme.secondary
            )},
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White
        ),
        modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33,locale = "es")
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        AlertAdd(
            1.0f,
            2.0f,
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { }
        )
    }
}