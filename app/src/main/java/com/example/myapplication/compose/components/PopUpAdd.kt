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
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.getDate
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.db.models.TrifleModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertAdd(
    eur: Float,
    yen: Float,
    onDismissRequest: () -> Unit,
    onConfirmation: @Composable (TrifleModel) -> Unit
) {
    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val categoryName = remember { mutableStateOf("") }
    val selectedText = remember { mutableStateOf(categories[0]) }
    var isConfirmed by remember { mutableStateOf(false) }
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
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.add_purchase),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Start
                )

                CustomFormTextField(
                    text = itemName,
                    placeholder = stringResource(id = R.string.trifle),
                    label = stringResource(id = R.string.trifle_name)
                )
                CustomFormTextField(
                    text = storeName,
                    placeholder = stringResource(id = R.string.acme),
                    label = stringResource(id = R.string.store_name)
                )

                CustomDropdownMenu(selectedText = selectedText, categoryName = categoryName)

                Column(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)) {
                    ShowMoneyExchangeItem(eur = eur, yen = yen)
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    PopUpButton(stringResource(id = R.string.dismiss)) { onDismissRequest.invoke() }
                    PopUpButton(stringResource(id = R.string.accept)) { isConfirmed = true }
                }

                if (isConfirmed) {
                    isConfirmed = false
                    onConfirmation.invoke(
                        TrifleModel(
                            name = if (itemName.value.isNotBlank()) itemName.value else stringResource(
                                id = R.string.trifle
                            ),
                            storeName = if (storeName.value.isNotBlank()) storeName.value else stringResource(
                                id = R.string.acme
                            ),
                            dateOfCreation = getDate(),
                            category = if (categoryName.value.isNotBlank()) categoryName.value else stringResource(
                                id = selectedText.value
                            ),
                            yenPrice = formatText(yen),
                            eurPrice = formatText(eur)
                        )
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
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