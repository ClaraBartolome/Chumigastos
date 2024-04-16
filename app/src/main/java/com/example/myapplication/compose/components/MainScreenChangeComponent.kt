package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.parseValue
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.OpenSans

@Composable
fun MainScreenChangeComponent(
    isTopCard: Boolean = false,
    isTextFieldEnabled: Boolean = false,
    imageFlag: Int = R.drawable.image_eur_flag,
    textFieldWeight: Float = 1.8f,
    digit: MutableState<Float> = remember { mutableStateOf(1.0f) },
    text: MutableState<String> = remember { mutableStateOf("0123456789")},
    currencyName: String = "Euros",
    firstExchange: String = "1JPY = 0,00061EUR",
    secondExchange: String = "1EUR = 164,5JPY",
) {
    val maxChar = 9
    Card(
        modifier = Modifier
            .background(Color.Transparent)
            .wrapContentHeight(Alignment.CenterVertically),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.surface
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
            Row(modifier = Modifier.weight(2f)) {
                Image(painter = painterResource(id = imageFlag),
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Column(Modifier.padding(start = 16.dp).fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                    Text(text = currencyName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold)
                }
            }

            Column(modifier = Modifier.weight(textFieldWeight).padding(end = 8.dp)){
                OutlinedTextField(
                    value = text.value,
                    onValueChange = {
                        if(it.length <= maxChar){
                            val newText = it.replace(Regex("[^0-9.,]"), "") // Eliminar cualquier cosa que no sea digito o coma o punto
                            text.value = newText
                        }
                    },
                    textStyle = MaterialTheme.typography.titleMedium.copy(textAlign = TextAlign.End),
                    singleLine = true,
                    enabled = isTextFieldEnabled,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledBorderColor = MaterialTheme.colorScheme.onSurface,
                        errorBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    )
                )
            }
        }

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).padding(bottom = 8.dp)){
            if(!isTopCard){
                Text(text = firstExchange,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal)
                Text(text = secondExchange,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            MainScreenChangeComponent()
        }
    }
}