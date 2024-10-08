package com.example.myapplication.compose.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
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
import com.example.myapplication.db.models.CategoryModel
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily

@Composable
fun ShoppingCartScreen(
    itemsList: State<List<TrifleModel>?> = remember {
        mutableStateOf(listOf())
    },
    storeName: MutableState<String> = remember { mutableStateOf("") },
    isTotalItemsList: MutableState<Boolean> = remember { mutableStateOf(false) },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    selectedOptions: SnapshotStateList<CategoryModel> = remember { mutableStateListOf<CategoryModel>() },
    onAddClick: () -> Unit = {},
    onBuyClick: () -> Unit = {},
    onLongClickOnItem: (TrifleModel) -> Unit = {},
) {
    var lastStoreName = ""
    itemsList.value?.let{ trifles ->

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

            if(trifles.isNotEmpty()){
                itemsIndexed(trifles){ index, trifleModel ->
                    if(selectedOptions.find { model -> model.name == trifleModel.category }?.isSelected == true){
                        Spacer(modifier = Modifier.height(8.dp))
                        if(lastStoreName.isBlank() || lastStoreName != trifleModel.storeName){
                            Text(
                                text = trifleModel.storeName,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                textDecoration = TextDecoration.Underline,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                            )
                            lastStoreName = trifleModel.storeName
                        }
                        ShoppingCartItem(
                            categoryId = CategoryModel(trifleModel.category).iconId,
                            itemName = trifleModel.name,
                            yenPrice = trifleModel.yenPrice,
                            eurPrice = trifleModel.eurPrice,
                            isEurToYen = isEurToYen,
                            onLongClickOnItem = { onLongClickOnItem.invoke(trifleModel) }
                        )
                        if(index == trifles.size -1){
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }else if(index == trifles.size -1){
                        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if(isTotalItemsList.value) stringResource(id = R.string.no_items_in_category) else stringResource(id = R.string.empty_cart),
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground,
                                fontFamily = poppinsFontFamily,
                                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                            )
                        }
                    }
                }
            }else{
                item {
                    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if(isTotalItemsList.value) stringResource(id = R.string.empty_totals) else stringResource(id = R.string.empty_cart),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontFamily = poppinsFontFamily,
                            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                        )
                    }
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
                TotalsBottomComponent(trifles, isEurToYen)
            }else{
                ShoppingCartBottomComponent(trifles, isEurToYen, onBuyClick)
            }
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