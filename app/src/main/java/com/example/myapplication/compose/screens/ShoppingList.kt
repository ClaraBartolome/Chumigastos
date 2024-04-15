package com.example.myapplication.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.categories
import com.example.myapplication.compose.components.CustomDropdownMenu
import com.example.myapplication.compose.components.CustomFormTextField
import com.example.myapplication.compose.components.CustomMoneyItemHorizontal
import com.example.myapplication.compose.components.DoubleBorderButton
import com.example.myapplication.compose.components.ShoppingListHeader
import com.example.myapplication.compose.components.ShoppingListItem
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingListScreen(eurExchange: Float,
                       yenExchange: Float,
                       onAddClick: (MutableList<TrifleModel>) -> Unit) {
    Column() {
        CustomFormTextField(text = remember { mutableStateOf("text") },
            placeholder = "placeholder", label = "label")
        CustomFormTextField(text = remember { mutableStateOf("text") },
            placeholder = "placeholder", label = "label")
        CustomDropdownMenu(selectedText = remember { mutableStateOf(categories[0]) }, categoryName = remember { mutableStateOf("text") })
        CustomMoneyItemHorizontal()

        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(top = 8.dp)){
            Column(Modifier.weight(1.5f)) {
                DoubleBorderButton(
                    label = stringResource(id = R.string.add_uppercase),
                    color = colorResource(id = R.color.green)
                ) {

                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_exchange),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxHeight(0.7f)
                    .weight(1f).clickable {  },
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
            )
        }

        LazyColumn(Modifier.padding(top = 8.dp).background(MaterialTheme.colorScheme.background)){
            stickyHeader {
                ShoppingListHeader()
            }
            items(10){
                ShoppingListItem(showTitle = it == 2, isFirstItem = it == 0)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33)
@Composable
private fun prevShoppingListScreen(){
    MyApplicationTheme {
        ShoppingListScreen(
            1.0f,
            1.0f,
            {})
}}