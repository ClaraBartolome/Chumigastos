package com.example.myapplication.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun AlertExchange(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
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
                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically , modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.edit_change),
                        modifier = Modifier
                            .padding(start = 16.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Start
                    )

                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.padding(end= 16.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_exchange),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.fillMaxSize(0.8f)
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.eur_to_jpy),
                    modifier = Modifier
                        .padding(start = 16.dp, top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(id = R.string.EUR_exchange),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.DarkGray
                )
                MoneyItemPopUp()

                Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically , modifier = Modifier
                    .fillMaxWidth()
                    .padding( horizontal = 16.dp)){
                    PopUpButton("Cancelar"){onDismissRequest.invoke()}
                    PopUpButton("Aceptar"){onConfirmation.invoke()}
                }
            }
        }
    }
}

@Composable
private fun PopUpButton(label: String, onClick: () -> Unit){
    Button(
        onClick = { onClick.invoke()},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        )) {
        Text(text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground)
    }
}



@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        AlertExchange(
            onDismissRequest = { /*TODO*/ },
            onConfirmation = { /*TODO*/ }
        )
    }
}