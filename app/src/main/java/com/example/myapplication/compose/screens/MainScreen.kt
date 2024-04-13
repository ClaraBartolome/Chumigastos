package com.example.myapplication.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.components.DoubleBorderButton
import com.example.myapplication.compose.components.MoneyItem
import com.example.myapplication.compose.getEurExchange
import com.example.myapplication.compose.getYenExchange
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen(
    yenExchange: MutableState<Float>,
    eurExchange: MutableState<Float>,
    onClickAdd: ()-> Unit,
    onClickList: ()-> Unit,
    onClickTotals: ()-> Unit) {
    val yenValue = remember { mutableStateOf<Float>(yenExchange.value) }
    val eurValue = remember { mutableStateOf<Float>(eurExchange.value) }
    Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            MoneyItem(eurValue, R.drawable.ic_euro) { yenValue.value = onUpdateCurrency(eurValue, eurExchange) }
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.ic_arrow), contentDescription = "", modifier = Modifier
                    .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Image(painter = painterResource(id = R.drawable.ic_arrow_upward), contentDescription = "", modifier = Modifier
                    .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
            MoneyItem(yenValue, R.drawable.ic_yen) { eurValue.value = onUpdateCurrency(yenValue, yenExchange) }
        }
        DoubleBorderButton(
            label = stringResource(id = R.string.add_uppercase),
            color = colorResource(id = R.color.green),
            onClickAdd)
        DoubleBorderButton(
            label = stringResource(id = R.string.shopping_list_uppercase),
            color = colorResource(id = R.color.pink),
            onClickList)
        DoubleBorderButton(
            label = stringResource(id = R.string.total_uppercase),
            color = colorResource(id = R.color.orange),
            onClickTotals)
    }
}



fun onUpdateCurrency(yen: MutableState<Float>, yenExchange: MutableState<Float>) = yen.value * yenExchange.value

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun prevMainScreen(){
    MyApplicationTheme {
        MainScreen(
            remember { mutableStateOf<Float>(1.0f)},
            remember { mutableStateOf<Float>(1.0f)},
            {}, {}, {})
    }
}