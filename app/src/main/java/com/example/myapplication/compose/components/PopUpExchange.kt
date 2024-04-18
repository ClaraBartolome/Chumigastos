package com.example.myapplication.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.compose.parseValue
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun PopUpExchange(
    eurChange: Float,
    yenChange: Float,
    onDismissRequest: () -> Unit,
    onConfirmation: (String, Boolean) -> Unit,
) {
    val exchangeEurYen = remember { mutableStateOf(true) }
    val input = remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_change),
                        modifier = Modifier
                            .padding(start = 16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Start
                    )

                    IconButton(
                        onClick = { exchangeEurYen.value = !exchangeEurYen.value },
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exchange),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.fillMaxSize(0.8f)
                        )
                    }
                }
                Text(
                    text = stringResource(id = if (exchangeEurYen.value) R.string.jpy_to_eur else R.string.eur_to_jpy),
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(id = if (exchangeEurYen.value) R.string.JPY_exchange else R.string.EUR_exchange),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                MoneyItemPopUp(exchangeEurYen, eurChange, yenChange) { text -> input.value = text }

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(Modifier.weight(1f)) {
                        PopUpButton(stringResource(id = R.string.dismiss)) { onDismissRequest.invoke() }

                    }
                    Column(Modifier.weight(1f)) {
                        PopUpButton(stringResource(id = R.string.accept)) {
                            onConfirmation.invoke(
                                input.value,
                                exchangeEurYen.value
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MoneyItemPopUp(
    eurToYen: MutableState<Boolean> = remember { mutableStateOf(false) },
    eurChange: Float = 1.0f,
    yenChange: Float = 1.0f,
    onUpdateNumber: (String) -> Unit = {}
) {
    var maxChar = 9
    var text by remember { mutableStateOf("") }
    OutlinedCard(
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
        border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        if (it.length <= maxChar) {
                            val newText = it.replace(Regex("[^0-9.,]"), "")
                            text = newText
                            onUpdateNumber.invoke(parseValue(newText).toString())
                        }
                    },
                    placeholder = {
                        Text(
                            text = (if (eurToYen.value) yenChange else eurChange).toString(),
                            color = MaterialTheme.colorScheme.primary.copy(0.5f)
                        )
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
            }
            VerticalDivider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary)

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = if (eurToYen.value) R.drawable.ic_euro else R.drawable.ic_yen),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
                )
            }
        }
    }
}

@Composable
fun PopUpButton(
    label: String,
    containerColor: Color = Color.Transparent,
    textColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    textWeight: FontWeight = FontWeight.Normal,
    onClick: () -> Unit) {
    Button(
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = textColor,
            fontWeight = textWeight
        )
    }
}


@ThemePreviews
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        PopUpExchange(1.0f, 1.0f,
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { text, exchange -> }
        )
    }
}