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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.TrifleApplicationViewModel
import com.example.myapplication.common.SortRadioOptions
import com.example.myapplication.common.TrifleScreens
import com.example.myapplication.common.categoriesModelMockUp
import com.example.myapplication.common.customRadioOptions
import com.example.myapplication.common.itemsMockUpList
import com.example.myapplication.compose.AddAllTrifles
import com.example.myapplication.compose.AddTrifle
import com.example.myapplication.compose.DeleteTrifle
import com.example.myapplication.compose.GetAllTriflesDateOfCreationDesc
import com.example.myapplication.compose.SortTree
import com.example.myapplication.compose.UpdateTrifle
import com.example.myapplication.compose.components.DropDownMenu
import com.example.myapplication.compose.components.PopUpExchange
import com.example.myapplication.compose.components.MainScreenBottomNav
import com.example.myapplication.compose.components.NavigationDrawerContent
import com.example.myapplication.compose.components.PopUpChoice
import com.example.myapplication.compose.components.PopUpChoiceButtons
import com.example.myapplication.compose.components.TopAppBarDefault
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.compose.sortedTrifleList
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
    //SORT
    val sortOption = remember { mutableStateOf<SortRadioOptions>(customRadioOptions[4]) }

    //Delete
    val showPopUpOptions = remember { mutableStateOf(false) }
    val showPopUpDelete = remember { mutableStateOf(false) }
    val isTrifleBeingDeleted = remember { mutableStateOf(false) }
    val itemChosen = remember { mutableStateOf<TrifleModel>(TrifleModel()) }

    //Edit
    val isEditScreen = remember { mutableStateOf(false) }
    val isItemFromCart = remember { mutableStateOf(false) }
    val isTrifleBeingUpdated = remember { mutableStateOf(false) }
    val originalItem = remember { mutableStateOf(TrifleModel()) }
    //DropdownMenu
    val showDropdownMenu = remember { mutableStateOf(false) }

    //CATEGORY
    val categoriesList = remember { mutableStateListOf<CategoryModel>() }
    categoriesList.addAll(createCategoriesList())

    //Search
    val searchText = remember { mutableStateOf("") }
    val isSearchOpen = remember { mutableStateOf(false) }

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
                    onNavigationIconClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onTotalOptions = {
                        showDropdownMenu.value = true
                    },
                    isSearchOpen = isSearchOpen,
                    searchText = searchText,
                    isEurToYen = isEurToYen,
                    onSearchInit = { sortedTrifleList(trifleList.value, searchText = searchText.value) },
                    onClickOnSearched = {
                        isEditScreen.value = true
                        isItemFromCart.value = false
                        isSearchOpen.value = false
                        searchText.value = ""
                        itemChosen.value = it
                        navController.navigate(TrifleScreens.EditExpense.name)
                    },
                    onTextChange = { text -> searchText.value = text },
                    onCloseClicked = { isSearchOpen.value = false }
                )
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
                    categoriesList.forEach {
                        it.isSelected = true
                    }
                    TAG = "MAIN_SCREEN"
                    GetAllTriflesDateOfCreationDesc(trifleViewModel = trifleApplicationViewModel, TAG)
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
                        itemsList = remember {
                            mutableStateOf(shoppingCartList.reversed())
                        },
                        storeName = storeName,
                        isEurToYen = isEurToYen,
                        selectedOptions = categoriesList,
                        onAddClick = {
                            addItemToShoppingCart.value = true
                            navController.navigate(TrifleScreens.AddExpense.name)},
                        onBuyClick = {
                            addAllItems.value = true
                        },
                        onLongClickOnItem = {
                            showPopUpOptions.value = true
                            shoppingCartItemChosen.value = it
                            originalItem.value = it
                        })
                    if (addAllItems.value){
                        addAllItems.value = false
                        AddAllTrifles(trifleViewModel = trifleApplicationViewModel, trifleList = shoppingCartList)
                        navController.navigate(TrifleScreens.Start.name)
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
                    SortTree(option = sortOption.value, viewModel = trifleApplicationViewModel)
                    ShoppingCartScreen(
                        itemsList = trifleList,
                        isTotalItemsList = isTotalItemsList,
                        isEurToYen = isEurToYen,
                        selectedOptions = categoriesList,
                        onAddClick = {},
                        onLongClickOnItem = {
                            showPopUpOptions.value = true
                            itemChosen.value = it
                        })
                    if(showPopUpOptions.value){
                        PopUpChoice(
                            onClickEdit = {
                                showPopUpOptions.value = false
                                isEditScreen.value = true
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
                                isTrifleBeingDeleted.value = true
                            },
                            onClickDismiss = {
                                showPopUpDelete.value = false
                            },
                            onDismissRequest = {
                                showPopUpDelete.value = false
                            }
                        )
                    }

                    if(isTrifleBeingDeleted.value){
                        isTrifleBeingDeleted.value = false
                        DeleteTrifle(trifleViewModel = trifleApplicationViewModel, trifle = itemChosen.value)
                        SortTree(option = sortOption.value, viewModel = trifleApplicationViewModel)
                    }

                    if(showDropdownMenu.value){
                        DropDownMenu(
                            showDropdownMenu,
                            orderChosen = sortOption,
                            onCategoryClick = {
                                showDropdownMenu.value = false
                                navController.navigate(TrifleScreens.CategoriesFilter.name)
                            },
                            onOrderClick = {
                                showDropdownMenu.value = false
                                navController.navigate(TrifleScreens.SortingConfig.name)
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
                            trifleModel.dateOfCreation = originalItem.value.dateOfCreation
                            val index = shoppingCartList.indexOf(originalItem.value)
                            shoppingCartList.remove(originalItem.value)
                            shoppingCartList.add(index, trifleModel)
                        }else{
                            itemChosen.value.apply {
                                name = trifleModel.name
                                this.storeName = trifleModel.storeName
                                category = trifleModel.category
                                dateOfModification = trifleModel.dateOfModification
                                yenPrice = trifleModel.yenPrice
                                eurPrice = trifleModel.eurPrice
                            }
                            isTrifleBeingUpdated.value = true
                        }

                    }

                    if(isTrifleBeingUpdated.value){
                        isTrifleBeingUpdated.value = false
                        UpdateTrifle(trifleViewModel = trifleApplicationViewModel, trifle = itemChosen.value, sortRadioOption = sortOption.value )
                        SortTree(option = sortOption.value, viewModel = trifleApplicationViewModel)
                        navController.popBackStack()
                    }

                }
                composable(route = TrifleScreens.SortingConfig.name) {
                    screen.value = TrifleScreens.SortingConfig
                    SortScreen(sortOption, customRadioOptions)
                }
                composable(route = TrifleScreens.CategoriesFilter.name) {
                    screen.value = TrifleScreens.CategoriesFilter
                    CategoriesScreen(categoriesList)
                }
            }
        }
    }
}

@Composable
private fun createCategoriesList(): List<CategoryModel> = listOf(
CategoryModel(name = stringResource(id = R.string.trifles)),
CategoryModel(name = stringResource(id = R.string.clothes)),
CategoryModel(name = stringResource(id = R.string.food)),
CategoryModel(name = stringResource(id = R.string.stationery)),
CategoryModel(name = stringResource(id = R.string.home))
)

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