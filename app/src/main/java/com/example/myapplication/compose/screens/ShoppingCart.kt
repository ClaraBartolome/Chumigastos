package com.example.myapplication.compose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.compose.components.CustomTextField
import com.example.myapplication.compose.components.ShoppingCartBottomComponent
import com.example.myapplication.compose.components.ShoppingCartItem
import com.example.myapplication.compose.components.TotalsBottomComponent
import com.example.myapplication.db.models.Category
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily

@Composable
fun ShoppingCartScreen(
    itemsList: MutableList<TrifleModel> = remember { mutableStateListOf() },
    storeName: MutableState<String> = remember { mutableStateOf("") },
    isTotalItemsList: MutableState<Boolean> = remember { mutableStateOf(false) },
    onAddClick: () -> Unit = {}
) {
    //itemsList.addAll(itemsMockUpList)
    val lastStoreName = remember { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(Modifier.padding(bottom = 100.dp)){
            if(!isTotalItemsList.value){
                item{
                    CustomTextField(
                        text = storeName,
                        placeholder = stringResource(id = R.string.acme),
                        label = stringResource(id = R.string.store_name)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            itemsIndexed(itemsList){ index, trifleModel ->
                Spacer(modifier = Modifier.height(8.dp))
                if(isTotalItemsList.value && (index == 0 || lastStoreName.value != trifleModel.storeName)){
                    Text(
                        text = trifleModel.storeName,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = poppinsFontFamily,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                    lastStoreName.value = trifleModel.storeName
                }
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
        if(!isTotalItemsList.value){
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
        }
        Column(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .background(MaterialTheme.colorScheme.tertiary)) {
            if(isTotalItemsList.value){
                TotalsBottomComponent(itemsList)
            }else{
                ShoppingCartBottomComponent(itemsList)
            }
        }
    }
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