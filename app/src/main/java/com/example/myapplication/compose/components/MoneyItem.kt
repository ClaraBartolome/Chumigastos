package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyItem(digit: MutableState<Double>, icon: Int = R.drawable.ic_euro, onUpdateNumber: () -> Unit) {
    var text by remember { mutableStateOf(formatText(digit.value)) }
    var previousValue = remember { mutableStateOf(digit.value) }
    LaunchedEffect(digit.value) {
        // Se ejecutará cuando el valor de digit cambie
        text = formatText(digit.value)
    }
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
        Row(modifier = Modifier
            .height(intrinsicSize = IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Column(modifier = Modifier
                .weight(1f)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        val newText = it.text.replace(Regex("[^\\d.]"), "")
                        text = TextFieldValue(newText, it.selection)
                        if(previousValue.value != parseValue(it.text)){
                            previousValue.value = parseValue(it.text)
                            digit.value = parseValue(it.text)
                            onUpdateNumber.invoke()
                        }
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

            Column(modifier = Modifier
                .padding(horizontal = 8.dp)){
                Image(painter = painterResource(id = icon), contentDescription = "", modifier = Modifier
                    .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}


private fun formatText(value: Double): TextFieldValue {
    val formattedValue = if (value % 1 == 0.0) {
        value.toInt().toString()
    } else {
        String.format(Locale.getDefault(), "%.2f", value)
    }
    return TextFieldValue(formattedValue, TextRange(formattedValue.length))
}

private fun parseValue(text: String): Double {
    val formattedText = text.replace(Regex("[^\\d.]"), "")
    val parsedValue = if (formattedText.isNotEmpty()) {
        try {
            formattedText.toDouble()
        } catch (e: NumberFormatException) {
            0.0
        }
    } else {
        0.0
    }
    return if (parsedValue % 1 == 0.0) parsedValue.toInt().toDouble() else parsedValue
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            MoneyItem(
                remember { mutableStateOf<Double>(1.0) },
                R.drawable.ic_euro, {})
            MoneyItem(
                remember { mutableStateOf<Double>(1.0) },
                R.drawable.ic_yen, {})
        }
    }
}