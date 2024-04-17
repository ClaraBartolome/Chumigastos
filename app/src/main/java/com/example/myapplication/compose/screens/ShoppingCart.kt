package com.example.myapplication.compose.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.compose.components.CustomTextField
import com.example.myapplication.compose.components.ShoppingCartItem
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.db.models.Category
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShoppingCartScreen(
    itemsList: MutableList<TrifleModel> = remember { mutableStateListOf() },
    storeName: MutableState<String> = remember { mutableStateOf("") },
    onAddClick: () -> Unit = {}
) {
    //itemsList.addAll(itemsMockUpList)
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(Modifier.padding(bottom = 100.dp)){
            item{
                CustomTextField(
                    text = storeName,
                    placeholder = stringResource(id = R.string.acme),
                    label = stringResource(id = R.string.store_name)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            itemsIndexed(itemsList){ index, trifleModel ->
                Spacer(modifier = Modifier.height(8.dp))
                ShoppingCartItem(
                    categoryId = Category(trifleModel.category).iconId,
                    itemName = trifleModel.name,
                    yenPrice = trifleModel.yenPrice,
                    eurPrice = trifleModel.eurPrice
                )
                if(index == itemsList.size -1){
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(bottom = 108.dp, end = 8.dp)){
            FloatingActionButton(
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 6.dp,
                    focusedElevation = 6.dp,
                    hoveredElevation = 6.dp
                ),
                onClick = { onAddClick.invoke() }) {
                Icon(Icons.Filled.Add, "")
            }
        }
        Column(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.tertiary)) {
            Text(
                text = stringResource(id = R.string.total_items, itemsList.size),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                fontWeight = FontWeight.SemiBold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()){
                Column(modifier = Modifier
                    .fillMaxHeight()) {
                    Text(
                        text = formatTextCurrency(calculateYenTotal(itemsList), Locale.JAPAN),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                    Text(
                        text = formatTextCurrency(calculateEurTotal(itemsList), Locale("es", "ES")),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                    )
                }


                Button(
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {

                    }) {
                    Text(
                        text = stringResource(id = R.string.buy_everything),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onTertiaryContainer,
                        textAlign = TextAlign.Center,
                        fontFamily = poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .widthIn(min = 60.dp)
                    )
                }
            }
        }
    }
}

private fun calculateYenTotal(itemsList: MutableList<TrifleModel>): Float{
    var total = 0f
    itemsList.forEach {item ->
        total += parseValue(item.yenPrice)
    }
    return total
}

private fun calculateEurTotal(itemsList: MutableList<TrifleModel>): Float{
    var total = 0f
    itemsList.forEach {item ->
        total += parseValue(item.eurPrice)
    }
    return total
}

@ThemePreviews
@Composable
private fun prevShoppingListScreen() {
    MyApplicationTheme {
        Surface {
            ShoppingCartScreen()
        }
    }
}