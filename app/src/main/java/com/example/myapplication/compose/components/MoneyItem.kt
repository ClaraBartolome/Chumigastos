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
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoneyItem(digit: MutableState<Float>, icon: Int = R.drawable.ic_euro, onUpdateNumber: () -> Unit) {
    var text by remember { mutableStateOf(formatText(digit.value)) }
    val previousValue = remember { mutableStateOf(digit.value) }
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
                        val newText = it.replace(Regex("[^0-9.,]"), "") // Eliminar cualquier cosa que no sea digito o coma o punto
                        val currentValue = parseValue(newText)
                        text = formatText(currentValue)
                        if(currentValue != previousValue.value){
                            previousValue.value = currentValue
                            digit.value = currentValue
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

@Composable
fun MoneyItemPopUp(eurToYen: MutableState<Boolean>, eurChange: Float, yenChange: Float, onUpdateNumber: (String) -> Unit){
    var text by remember { mutableStateOf("") }
    OutlinedCard (
        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
    ) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .height(IntrinsicSize.Min)) {
            Column(modifier = Modifier.weight(1f)) {
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        val newText = it.replace(Regex("[^0-9.,]"), "")
                        text = newText
                        onUpdateNumber.invoke(parseValue(newText).toString())
                    },
                    placeholder = {
                        Text(text = (if (eurToYen.value) eurChange else yenChange).toString(),
                            color = Color.LightGray
                        )},
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
                .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center){
                Image(painter = painterResource(id = if(eurToYen.value) R.drawable.ic_euro else R.drawable.ic_yen), contentDescription = "",
                    modifier = Modifier
                        .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    }
}


private fun parseValue(text: String): Float{
    // Encontrar la última coma en la cadena
    val lastCommaIndex = text.lastIndexOf(",")

    // Separar la parte entera de la parte decimal
    val integerPart = if (lastCommaIndex != -1) text.substring(0, lastCommaIndex) else text
    val decimalPart = if (lastCommaIndex != -1) text.substring(lastCommaIndex + 1) else ""

    // Eliminar comas y puntos solo de la parte entera
    val cleanedIntegerPart = integerPart.replace(Regex("[,.]"), "")

    // Reunir la parte entera limpia y la parte decimal
    val cleanedText = if (decimalPart.isNotEmpty()) {
        "$cleanedIntegerPart.${decimalPart.replace(".", "")}"
    } else {
        cleanedIntegerPart
    }

    // Convertir la cadena limpia a un número flotante
    return cleanedText.toFloatOrNull() ?: 0.0f
}

private fun formatText(value: Float): String {
    return if (value % 1.0f == 0.0f) {
        value.toInt().toString()
    } else {
        String.format(Locale.getDefault(), "%.2f", value)
    }
}

private fun formatTextExchange(value: Float, type:Boolean): String {
    return if (value % 1.0f == 0.0f) {
        value.toInt().toString()
    } else if(type) {
        String.format(Locale.getDefault(), "%.2f", value)
    }else{
        String.format(Locale.getDefault(), "%.5f", value)
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            MoneyItem(
                remember { mutableStateOf<Float>(1.0f) },
                R.drawable.ic_euro, {})
            MoneyItem(
                remember { mutableStateOf<Float>(1.0f) },
                R.drawable.ic_yen, {})
        }
    }
}