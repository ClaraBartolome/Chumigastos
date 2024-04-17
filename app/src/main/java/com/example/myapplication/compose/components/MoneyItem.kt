package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.parseValue
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyItem(
    digit: MutableState<Float>, text: MutableState<String>,
    icon: Int = R.drawable.ic_euro, onUpdateNumber: () -> Unit
) {

    val previousValue = remember { mutableStateOf(digit.value) }
    var currentValue = 0.0f
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(Color.Transparent)
            .wrapContentHeight(Alignment.CenterVertically),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            disabledContainerColor = Color.White,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        val newText = it.replace(
                            Regex("[^0-9.,]"),
                            ""
                        ) // Eliminar cualquier cosa que no sea digito o coma o punto
                        currentValue = parseValue(newText)
                        text.value = newText
                    },
                    textStyle = MaterialTheme.typography.titleMedium,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {

                            previousValue.value = currentValue
                            digit.value = currentValue
                            onUpdateNumber.invoke()

                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calculate),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.fillMaxSize(0.8f)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledBorderColor = Color.Transparent,
                        errorBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }

            VerticalDivider(thickness = 2.dp, color = Color.LightGray)

            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
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
    var text by remember { mutableStateOf("") }
    OutlinedCard(
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
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
                        val newText = it.replace(Regex("[^0-9.,]"), "")
                        text = newText
                        onUpdateNumber.invoke(parseValue(newText).toString())
                    },
                    placeholder = {
                        Text(
                            text = (if (eurToYen.value) eurChange else yenChange).toString(),
                            color = Color.LightGray
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
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
            VerticalDivider(thickness = 2.dp, color = Color.LightGray)

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
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}

@Composable
fun ShowMoneyExchangeItem(eur: Float, yen: Float) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        OutlinedCard(
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    text = formatText(eur),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(all = 8.dp)
                )
                VerticalDivider(thickness = 2.dp, color = Color.LightGray)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_euro),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight(0.7f),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }

        OutlinedCard(
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(IntrinsicSize.Min)
            ) {
                Text(
                    text = formatText(yen),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(all = 8.dp)
                )
                VerticalDivider(thickness = 2.dp, color = Color.LightGray)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_yen),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxHeight(0.7f),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            MoneyItem(
                remember { mutableStateOf<Float>(1.0f) },
                remember { mutableStateOf("1") },
                R.drawable.ic_euro, {})
            MoneyItem(
                remember { mutableStateOf<Float>(1.0f) },
                remember { mutableStateOf("1.0f") },
                R.drawable.ic_yen, {})
            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp)) {
                ShowMoneyExchangeItem(eur = 1.67f, yen = 1.67f)
            }
        }
    }
}