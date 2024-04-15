package com.example.myapplication.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.compose.screens.MainScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun ShoppingListHeader() {
    Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier.wrapContentHeight()) {
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()){
            Column(modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
                verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.total_uppercase),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            Column(Modifier.weight(3f, true).padding(all = 16.dp), verticalArrangement = Arrangement.Center) {
                ShowMoneyExchangeItem(eur = 100.0f, yen = 100.64f)
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun prevShoppingListHeader(){
    MyApplicationTheme {
        ShoppingListHeader()
    }
}