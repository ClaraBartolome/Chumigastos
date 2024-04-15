package com.example.myapplication.compose.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.ChumiScreens
import com.example.myapplication.compose.components.AlertAdd
import com.example.myapplication.compose.components.AlertExchange
import com.example.myapplication.compose.components.TopAppBarDefault

@Composable
fun UICompose(
    yenExchange: MutableState<Float>,
    eurExchange: MutableState<Float>,
    saveYenExchange: () -> Unit,
    saveEurExchange: () -> Unit,
) {
    val navController = rememberNavController()
    val ctx = LocalContext.current

    //Toolbar
    val screen = remember { mutableStateOf(ChumiScreens.Start) }

    //Edit exchange
    val isPopUpExchangeOpen = remember { mutableStateOf(false) }

    //Add Item
    val isPopUpAddItemOpen = remember { mutableStateOf(false) }

    //Price
    val yenValue = remember { mutableStateOf<Float>(100f) }
    val eurValue = remember { mutableStateOf<Float>(100/ eurExchange.value) }
    Scaffold(
        topBar = { TopAppBarDefault(navController = navController, screen = screen.value, isPopUpExchangeOpen) },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ChumiScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ChumiScreens.Start.name) {
                screen.value = ChumiScreens.Start
                Box(
                    modifier = with (Modifier){
                        fillMaxSize()
                            .paint(
                                // Replace with your image id
                                painterResource(id = R.drawable.bg_image_test),
                                contentScale = ContentScale.FillBounds)

                    }) {
                    MainScreen(
                        yenExchange,
                        eurExchange,
                        yenValue,
                        eurValue,
                        onClickAdd = { isPopUpAddItemOpen.value = true },
                        onClickList = { navController.navigate(ChumiScreens.ShoppingList.name) },
                        onClickTotals = { createToast(ctx) }
                    )
                    if (isPopUpExchangeOpen.value) {
                        AlertExchange(
                            eurExchange.value,
                            yenExchange.value,
                            onDismissRequest = {
                                isPopUpExchangeOpen.value = false
                            }) { text, exchangeEurYen ->
                            if (text.isNotBlank() && exchangeEurYen) {
                                eurExchange.value = text.toFloat()
                                yenExchange.value = 1/eurExchange.value
                                saveEurExchange.invoke()
                            } else if (!exchangeEurYen) {
                                yenExchange.value = text.toFloat()
                                eurExchange.value = 1/yenExchange.value
                                saveYenExchange.invoke()
                            }
                            isPopUpExchangeOpen.value = false
                        }
                    }
                    if(isPopUpAddItemOpen.value){
                        AlertAdd(
                            eurValue.value,
                            yenValue.value,
                            onDismissRequest = { isPopUpAddItemOpen.value = false})
                        { trifleModel ->
                            createToast(ctx, trifleModel.name)
                            isPopUpAddItemOpen.value = false
                        }
                    }
                }
                }

            composable(route = ChumiScreens.ShoppingList.name){
                screen.value = ChumiScreens.ShoppingList
                Box(
                    modifier = with (Modifier){
                        fillMaxSize()
                            .paint(
                                // Replace with your image id
                                painterResource(id = R.drawable.bg_image_test),
                                contentScale = ContentScale.FillBounds)

                    }) {
                    ShoppingListScreen(
                        eurExchange = eurExchange.value,
                        yenExchange = yenExchange.value,
                        onAddClick = {})
                }
            }
        }
    }
}

private fun createToast(context: Context, label: String = "Próximamente") {
    Toast.makeText(context, label, Toast.LENGTH_SHORT).show()
}