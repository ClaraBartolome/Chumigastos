package com.example.myapplication.compose.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.TriffleScreens
import com.example.myapplication.common.itemsMockUpList
import com.example.myapplication.compose.components.AlertExchange
import com.example.myapplication.compose.components.MainScreenBottomNav
import com.example.myapplication.compose.components.NavigationDrawerContent
import com.example.myapplication.compose.components.TopAppBarDefault
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

@Composable
fun UICompose(
    yenExchange: MutableState<Float> = remember { mutableStateOf(1.0f) },
    eurExchange: MutableState<Float> = remember { mutableStateOf(1.0f) },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    saveYenExchange: () -> Unit = {},
    saveEurExchange: () -> Unit = {},
    saveIsEurToYen: () -> Unit = {}
) {
    val navController = rememberNavController()
    val ctx = LocalContext.current

    //Toolbar
    val screen = remember { mutableStateOf(TriffleScreens.Start) }

    //Edit exchange
    val isPopUpExchangeOpen = remember { mutableStateOf(false) }

    //Add Item
    val isPopUpAddItemOpen = remember { mutableStateOf(false) }

    //Price
    val yenValue = remember { mutableStateOf<Float>(100f) }
    val eurValue = remember { mutableStateOf<Float>(100 / eurExchange.value) }

    //Shopping Cart
    val itemsList = remember { mutableStateListOf<TrifleModel>() }
    itemsList.addAll(itemsMockUpList)
    val addItemToShoppingCart = remember { mutableStateOf(false) }
    val storeName = remember { mutableStateOf("") }

    //Totals
    val isTotalItemsList: MutableState<Boolean> = remember { mutableStateOf(true) }
    val totalItemsList = remember { mutableStateListOf<TrifleModel>() }
    totalItemsList.addAll(itemsMockUpList)

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(
                scope = scope,
                drawerState = drawerState,
                onSelectItem = { itemIndex ->
                    NavigationDrawerOnSelectOption(
                        itemIndex,
                        navController
                    )
                })
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                TopAppBarDefault(
                    navController = navController,
                    screen = screen.value,
                    isPopUpExchangeOpen
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            },
            bottomBar = {
                if (screen.value == TriffleScreens.Start) {
                    MainScreenBottomNav(
                        onClickAdd = { navController.navigate(TriffleScreens.AddExpense.name) },
                        onClickChange = {
                            isEurToYen.value = !isEurToYen.value
                            saveIsEurToYen.invoke()
                        },
                        onClickEdit = { isPopUpExchangeOpen.value = true },
                    )
                }
            },
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            modifier = Modifier.background(Color.Transparent)
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = TriffleScreens.Start.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = TriffleScreens.Start.name) {
                    screen.value = TriffleScreens.Start
                    storeName.value = ""
                    Box(
                        modifier = with(Modifier) {
                            fillMaxSize()
                                .paint(
                                    // Replace with your image id
                                    painterResource(id = if (isSystemInDarkTheme()) R.drawable.image_bg_dark_test else R.drawable.bg_image_test),
                                    contentScale = ContentScale.FillBounds
                                )
                                .background(if (isSystemInDarkTheme()) Color.Black.copy(0.3f) else Color.Transparent)

                        }) {
                        MainScreen(
                            yenExchange,
                            eurExchange,
                            yenValue,
                            eurValue,
                            isEurToYen,
                            onClickAdd = { navController.navigate(TriffleScreens.AddExpense.name) },
                            onClickList = { navController.navigate(TriffleScreens.ShoppingList.name) },
                            onClickTotals = { navController.navigate(TriffleScreens.Totals.name) }
                        )
                        if (isPopUpExchangeOpen.value) {
                            AlertExchange(
                                eurExchange.value,
                                yenExchange.value,
                                onDismissRequest = {
                                    isPopUpExchangeOpen.value = false
                                }) { text, exchangeEurYen ->
                                if (text.isNotBlank() && exchangeEurYen) {
                                    yenExchange.value = text.toFloat()
                                    eurExchange.value = 1 / yenExchange.value
                                    saveYenExchange.invoke()
                                } else if (!exchangeEurYen) {
                                    eurExchange.value = text.toFloat()
                                    yenExchange.value = 1 / eurExchange.value
                                    saveEurExchange.invoke()
                                }
                                isPopUpExchangeOpen.value = false
                            }
                        }
                    }
                }
                composable(route = TriffleScreens.ShoppingList.name) {
                    screen.value = TriffleScreens.ShoppingList
                    ShoppingCartScreen(
                        itemsList = itemsList,
                        storeName = storeName,
                        onAddClick = {
                            addItemToShoppingCart.value = true
                            navController.navigate(TriffleScreens.AddExpense.name)})
                }
                composable(route = TriffleScreens.AddExpense.name) {
                    screen.value = TriffleScreens.AddExpense
                    AddExpenseScreen(
                        eurValue, yenValue, yenExchange, eurExchange, isEurToYen, storeName
                    ) { trifleModel ->
                        CreateToast(ctx, trifleModel.toString())
                        if(addItemToShoppingCart.value){
                            addItemToShoppingCart.value = false
                            itemsList.add(trifleModel)
                        }
                        navController.popBackStack()
                    }
                }
                composable(TriffleScreens.Totals.name){
                    screen.value = TriffleScreens.Totals
                    ShoppingCartScreen(
                        itemsList = totalItemsList,
                        isTotalItemsList = isTotalItemsList,
                        onAddClick = {})
                }
            }
        }
    }
}

private fun NavigationDrawerOnSelectOption(selectedOption: Int, navController: NavController) {
    when (selectedOption) {
        0 -> {
            navController.navigate(TriffleScreens.AddExpense.name)
        }

        1 -> {
            navController.navigate(TriffleScreens.ShoppingList.name)
        }

        2 -> {navController.navigate(TriffleScreens.Totals.name)}
    }
}

private fun CreateToast(context: Context, label: String = "Próximamente") {
    Toast.makeText(context, label, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
@Composable
private fun prevMainScreen() {
    MyApplicationTheme {
        UICompose()
    }
}