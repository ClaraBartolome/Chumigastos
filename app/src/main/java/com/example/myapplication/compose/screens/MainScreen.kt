package com.example.myapplication.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.components.DoubleBorderButton
import com.example.myapplication.compose.components.MainScreenChangeComponent
import com.example.myapplication.compose.components.MainScreenTotalsComponent
import com.example.myapplication.compose.components.MoneyItem
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.parseValue
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen(
    yenExchange: MutableState<Float>,
    eurExchange: MutableState<Float>,
    yenValue: MutableState<Float>,
    eurValue: MutableState<Float>,
    onClickAdd: ()-> Unit,
    onClickList: ()-> Unit,
    onClickTotals: ()-> Unit) {

    val textYen = remember { mutableStateOf(formatText(yenValue.value)) }
    val textEur = remember { mutableStateOf(formatText(eurValue.value)) }
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Top, modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)) {

            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(id = R.string.currency_exchange),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold)

                Text(text = stringResource(id = R.string.calculate),
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        eurValue.value = parseValue(textEur.value)
                        yenValue.value = onUpdateCurrency(eurValue, eurExchange)
                        textYen.value = formatText(yenValue.value)
                        textEur.value = formatText(eurValue.value)
                    })
            }



            Column(Modifier.padding(vertical = 16.dp)) {
                MainScreenChangeComponent(
                    isTopCard = true,
                    text = textEur,
                    isTextFieldEnabled = true,
                    textFieldWeight = 2.0f,
                    currencyName = stringResource(id = R.string.eur),
                )
            }
            MainScreenChangeComponent(
                isTopCard = false,
                isTextFieldEnabled = false,
                currencyName = stringResource(id = R.string.yen),
                imageFlag = R.drawable.image_japan_flag,
                text = textYen
            )
        }

        HorizontalDivider(thickness = 2.dp, color = Color.LightGray, modifier = Modifier.padding(vertical = 32.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {
            MainScreenTotalsComponent(onClickSeeAll = onClickTotals)
        }
    }
}



private fun onUpdateCurrency(yen: MutableState<Float>, yenExchange: MutableState<Float>) = yen.value * yenExchange.value



@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
@Composable
private fun prevMainScreen(){
    MyApplicationTheme {
        MainScreen(
            remember { mutableStateOf<Float>(1.0f)},
            remember { mutableStateOf<Float>(1.0f)},
            remember { mutableStateOf<Float>(1.0f)},
            remember { mutableStateOf<Float>(1.0f)},
            {}, {}, {})
    }
}