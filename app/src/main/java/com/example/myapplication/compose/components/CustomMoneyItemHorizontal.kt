package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun CustomMoneyItemHorizontal() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
    ) {
        Column(Modifier.weight(3f)) {
            CustomOutlinedTextField(value = remember { mutableStateOf("123456,87") }) {}
        }
        Image(
            painter = painterResource(id = R.drawable.ic_arrow_forward),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .weight(1f)
                .padding(horizontal = 8.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
        )

        Column(Modifier.weight(3f)) {
            CustomOutlinedText(text = formatText(142345.55f), iconId = R.drawable.ic_yen)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        Column {
            CustomMoneyItemHorizontal()
        }
    }
}