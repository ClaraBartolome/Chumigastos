package com.example.myapplication.compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun DoubleBorderButton(label: String = "AÑADIR", color: Color = Color.Green, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = color,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp)
            .clickable { onClick.invoke() },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
            ),
            border = BorderStroke(4.dp, Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(all = 8.dp)
        ) {
            Text(
                text = label,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            DoubleBorderButton() {}
        }
    }
}