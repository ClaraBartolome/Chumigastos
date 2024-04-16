package com.example.myapplication.compose.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.common.ChumiScreens
import com.example.myapplication.compose.components.AlertAdd
import com.example.myapplication.compose.components.AlertExchange
import com.example.myapplication.compose.components.MainScreenBottomNav
import com.example.myapplication.compose.components.NavigationDrawerContent
import com.example.myapplication.compose.components.TopAppBarDefault
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
    val screen = remember { mutableStateOf(ChumiScreens.Start) }

    //Edit exchange
    val isPopUpExchangeOpen = remember { mutableStateOf(false) }

    //Add Item
    val isPopUpAddItemOpen = remember { mutableStateOf(false) }

    //Price
    val yenValue = remember { mutableStateOf<Float>(100f) }
    val eurValue = remember { mutableStateOf<Float>(100 / eurExchange.value) }


    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(drawerContent = {
        NavigationDrawerContent(scope = scope, drawerState = drawerState, onSelectItem = {itemIndex -> NavigationDrawerOnSelectOption(itemIndex, navController) })
    },
        drawerState = drawerState) {
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
                if(screen.value == ChumiScreens.Start){
                    MainScreenBottomNav(
                        onClickAdd = { createToast(ctx) },
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
                startDestination = ChumiScreens.Start.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = ChumiScreens.Start.name) {
                    screen.value = ChumiScreens.Start
                    Box(
                        modifier = with(Modifier) {
                            fillMaxSize()
                                .paint(
                                    // Replace with your image id
                                    painterResource(id = R.drawable.bg_image_test),
                                    contentScale = ContentScale.FillBounds
                                )

                        }) {
                        MainScreen(
                            yenExchange,
                            eurExchange,
                            yenValue,
                            eurValue,
                            isEurToYen,
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
                                    yenExchange.value = 1 / eurExchange.value
                                    saveEurExchange.invoke()
                                } else if (!exchangeEurYen) {
                                    yenExchange.value = text.toFloat()
                                    eurExchange.value = 1 / yenExchange.value
                                    saveYenExchange.invoke()
                                }
                                isPopUpExchangeOpen.value = false
                            }
                        }
                        if (isPopUpAddItemOpen.value) {
                            AlertAdd(
                                eurValue.value,
                                yenValue.value,
                                onDismissRequest = { isPopUpAddItemOpen.value = false })
                            { trifleModel ->
                                createToast(ctx, trifleModel.name)
                                isPopUpAddItemOpen.value = false
                            }
                        }
                    }
                }

                composable(route = ChumiScreens.ShoppingList.name) {
                    screen.value = ChumiScreens.ShoppingList
                    Box(
                        modifier = with(Modifier) {
                            fillMaxSize()
                                .paint(
                                    // Replace with your image id
                                    painterResource(id = R.drawable.bg_image_test),
                                    contentScale = ContentScale.FillBounds
                                )

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
}

private fun NavigationDrawerOnSelectOption(selectedOption: Int, navController: NavController){
    when(selectedOption){
        0-> {/*AÑADIR ITEM*/}
        1-> {navController.navigate(ChumiScreens.ShoppingList.name)}
        2-> {/*IR A TOTALES*/}
    }
}

private fun createToast(context: Context, label: String = "Próximamente") {
    Toast.makeText(context, label, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
@Composable
private fun prevMainScreen() {
    MyApplicationTheme {
        UICompose()
    }
}