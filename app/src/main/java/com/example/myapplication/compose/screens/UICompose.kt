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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.TrifleApplicationViewModel
import com.example.myapplication.common.TrifleScreens
import com.example.myapplication.common.itemsMockUpList
import com.example.myapplication.compose.AddAllTrifles
import com.example.myapplication.compose.AddTrifle
import com.example.myapplication.compose.GetAllTrifles
import com.example.myapplication.compose.components.PopUpExchange
import com.example.myapplication.compose.components.MainScreenBottomNav
import com.example.myapplication.compose.components.NavigationDrawerContent
import com.example.myapplication.compose.components.PopUpChoice
import com.example.myapplication.compose.components.PopUpChoiceButtons
import com.example.myapplication.compose.components.TopAppBarDefault
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.db.models.CategoryModel
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch
import java.util.Locale

var TAG = "TRIFLE_APPLICATION"

@Composable
fun UICompose(
    yenExchange: MutableState<Float> = remember { mutableStateOf(1.0f) },
    eurExchange: MutableState<Float> = remember { mutableStateOf(1.0f) },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    trifleApplicationViewModel: TrifleApplicationViewModel,
    saveYenExchange: () -> Unit = {},
    saveEurExchange: () -> Unit = {},
    saveIsEurToYen: () -> Unit = {}
) {
    val navController = rememberNavController()
    val ctx = LocalContext.current

    //ROOM
    val lifecycleOwner = LocalLifecycleOwner.current
    val trifleList = trifleApplicationViewModel.allTrifles.observeAsState()


    //Toolbar
    val screen = remember { mutableStateOf(TrifleScreens.Start) }

    //Edit exchange
    val isPopUpExchangeOpen = remember { mutableStateOf(false) }

    //Price
    val yenValue = remember { mutableStateOf<Float>(100f) }
    val eurValue = remember { mutableStateOf<Float>(100 / eurExchange.value) }

    //Shopping Cart
    val shoppingCartList = remember { mutableStateListOf<TrifleModel>() }
    val addItemToShoppingCart = remember { mutableStateOf(false) }
    val storeName = remember { mutableStateOf("") }
    val addAllItems = remember { mutableStateOf(false) }
    val shoppingCartItemChosen = remember { mutableStateOf<TrifleModel>(TrifleModel()) }

    //Totals
    val isTotalItemsList: MutableState<Boolean> = remember { mutableStateOf(true) }
    val totalItemsList = remember { mutableStateListOf<TrifleModel>() }
    totalItemsList.addAll(itemsMockUpList)

    //Delete
    val showPopUpOptions = remember { mutableStateOf(false) }
    val showPopUpDelete = remember { mutableStateOf(false) }
    val itemChosen = remember { mutableStateOf<TrifleModel>(TrifleModel()) }

    //Edit
    val isEditScreen = remember { mutableStateOf(false) }
    val isItemFromCart = remember { mutableStateOf(false) }
    val originalItem = remember { mutableStateOf(TrifleModel()) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            NavigationDrawerContent(
                scope = scope,
                drawerState = drawerState,
                onSelectItem = { itemIndex ->
                    navigationDrawerOnSelectOption(
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
                if (screen.value == TrifleScreens.Start) {
                    MainScreenBottomNav(
                        onClickAdd = { navController.navigate(TrifleScreens.AddExpense.name) },
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
                startDestination = TrifleScreens.Start.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = TrifleScreens.Start.name) {
                    screen.value = TrifleScreens.Start
                    storeName.value = ""
                    shoppingCartList.clear()
                    TAG = "MAIN_SCREEN"
                    GetAllTrifles(trifleViewModel = trifleApplicationViewModel, TAG)
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
                            totalEur = formatTextCurrency(calculateTotalEur(trifleList.value), Locale("es", "ES")),
                            totalYen = formatTextCurrency(calculateTotalYen(trifleList.value), Locale.JAPAN),
                            onClickTotals = { navController.navigate(TrifleScreens.Totals.name) }
                        )
                        if (isPopUpExchangeOpen.value) {
                            PopUpExchange(
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
                composable(route = TrifleScreens.ShoppingList.name) {
                    screen.value = TrifleScreens.ShoppingList
                    ShoppingCartScreen(
                        itemsList = shoppingCartList,
                        storeName = storeName,
                        isEurToYen = isEurToYen,
                        onAddClick = {
                            addItemToShoppingCart.value = true
                            navController.navigate(TrifleScreens.AddExpense.name)},
                        onBuyClick = {
                            addAllItems.value = true
                            navController.navigate(TrifleScreens.Start.name)
                        },
                        onLongClickOnItem = {
                            showPopUpOptions.value = true
                            shoppingCartItemChosen.value = it
                            originalItem.value = it
                        })
                    if (addAllItems.value){
                        addAllItems.value = false
                        AddAllTrifles(trifleViewModel = trifleApplicationViewModel, trifleList = shoppingCartList)
                    }

                    if(showPopUpOptions.value){
                        PopUpChoice(
                            onClickEdit = {
                                showPopUpOptions.value = false
                                isEditScreen.value = true
                                isItemFromCart.value = true
                                navController.navigate(TrifleScreens.EditExpense.name)
                            },
                            onClickDelete = {
                                showPopUpOptions.value = false
                                showPopUpDelete.value = true}
                        )
                    }

                    if(showPopUpDelete.value){
                        PopUpChoiceButtons(
                            onClickAccept = {
                                showPopUpDelete.value = false
                                shoppingCartList.remove(shoppingCartItemChosen.value)
                            },
                            onClickDismiss = {
                                showPopUpDelete.value = false
                            }
                        )
                    }

                }
                composable(route = TrifleScreens.AddExpense.name) {
                    screen.value = TrifleScreens.AddExpense
                    AddExpenseScreen(
                        eurValue,
                        yenValue,
                        yenExchange,
                        eurExchange,
                        isEurToYen,
                        storeName,
                        textEur = remember { mutableStateOf(formatText(eurValue.value)) },
                        textYen = remember { mutableStateOf(formatText(yenValue.value)) }
                    ) { trifleModel ->
                        if(addItemToShoppingCart.value){
                            addItemToShoppingCart.value = false
                            shoppingCartList.add(trifleModel)
                        }else{
                            AddTrifle(trifleViewModel = trifleApplicationViewModel, trifle = trifleModel)
                        }
                        navController.popBackStack()
                    }
                }
                composable(TrifleScreens.Totals.name){
                    screen.value = TrifleScreens.Totals
                    ShoppingCartScreen(
                        itemsList = trifleList.value,
                        isTotalItemsList = isTotalItemsList,
                        isEurToYen = isEurToYen,
                        onAddClick = {},
                        onLongClickOnItem = {
                            showPopUpOptions.value = true
                            itemChosen.value = it
                        })
                    if(showPopUpOptions.value){
                        PopUpChoice(
                            onClickEdit = {
                                showPopUpOptions.value = false
                                isEditScreen.value = false
                                isItemFromCart.value = false
                                navController.navigate(TrifleScreens.EditExpense.name)
                            },
                            onClickDelete = {
                                showPopUpOptions.value = false
                                showPopUpDelete.value = true},
                            onDismissRequest = {
                                showPopUpOptions.value = false
                            }
                        )
                    }

                    if(showPopUpDelete.value){
                        PopUpChoiceButtons(
                            onClickAccept = {
                                showPopUpDelete.value = false
                                trifleApplicationViewModel.deleteTrifle(itemChosen.value)
                            },
                            onClickDismiss = {
                                showPopUpDelete.value = false
                            },
                            onDismissRequest = {
                                showPopUpDelete.value = false
                            }
                        )
                    }
                }
                composable(TrifleScreens.EditExpense.name){
                    screen.value = TrifleScreens.EditExpense
                    val item = if(isItemFromCart.value) shoppingCartItemChosen else itemChosen
                    AddExpenseScreen(
                        yenExchange = yenExchange,
                        eurExchange = eurExchange,
                        isEurToYen = isEurToYen,
                        textEur = remember { mutableStateOf(item.value.eurPrice) },
                        textYen = remember { mutableStateOf(item.value.yenPrice) },
                        itemName = remember { mutableStateOf(item.value.name) },
                        storeName = remember { mutableStateOf(item.value.storeName) },
                        categoryNumber = remember { mutableStateOf(CategoryModel(item.value.category).position) },
                        yenValue = remember { mutableStateOf<Float>(parseValue(item.value.yenPrice)) },
                        eurValue = remember { mutableStateOf<Float>(parseValue(item.value.eurPrice)) },
                        isEditScreen = isEditScreen
                    ) { trifleModel ->
                        if(isItemFromCart.value){
                            isItemFromCart.value = false
                            val index = shoppingCartList.indexOf(originalItem.value)
                            shoppingCartList.remove(originalItem.value)
                            shoppingCartList.add(index, trifleModel)
                        }else{
                            trifleModel.id = itemChosen.value.id
                            trifleApplicationViewModel.updateTrifle(trifleModel)
                        }
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

private fun calculateTotalEur(list: List<TrifleModel>?): Float{
    var total = 0.0f
    list?.let { trifleList ->
        trifleList.forEach {
            total += parseValue(it.eurPrice)
        }
    }
    return total
}

private fun calculateTotalYen(list: List<TrifleModel>?): Float{
    var total = 0.0f
    list?.let { trifleList ->
        trifleList.forEach {
            total += parseValue(it.yenPrice)
        }
    }
    return total
}

private fun navigationDrawerOnSelectOption(selectedOption: Int, navController: NavController) {
    when (selectedOption) {
        0 -> {
            navController.navigate(TrifleScreens.AddExpense.name)
        }

        1 -> {
            navController.navigate(TrifleScreens.ShoppingList.name)
        }

        2 -> {navController.navigate(TrifleScreens.Totals.name)}
    }
}

private fun CreateToast(context: Context, label: String = "Próximamente") {
    Toast.makeText(context, label, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
@Composable
private fun prevMainScreen() {
    MyApplicationTheme {
        //UICompose()
    }
}