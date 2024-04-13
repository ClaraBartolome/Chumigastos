package com.example.myapplication.compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.components.MoneyItem
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen() {
    LazyColumn(){
        item {
            Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.padding(top = 64.dp)) {
                val numberEur = remember { mutableStateOf<Double>(1.0) }
                val numberYen = remember { mutableStateOf<Double>(1.0) }
                MoneyItem(numberEur, R.drawable.ic_euro) { numberYen.value = onUpdateYen(numberEur) }
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    Image(painter = painterResource(id = R.drawable.ic_arrow), contentDescription = "", modifier = Modifier
                        .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                    Image(painter = painterResource(id = R.drawable.ic_arrow), contentDescription = "", modifier = Modifier
                        .size(32.dp), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    )
                }
                MoneyItem(numberYen, R.drawable.ic_yen) { numberEur.value = onUpdateEur(numberYen) }
            }
        }
    }
}

fun onUpdateEur(yen: MutableState<Double>) = yen.value / 164

fun onUpdateYen(eur: MutableState<Double>) = eur.value * 164

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
fun prevMainScreen(){
    MyApplicationTheme {
        MainScreen()
    }
}