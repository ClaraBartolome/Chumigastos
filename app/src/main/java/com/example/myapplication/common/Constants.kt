package com.example.myapplication.common

import com.example.myapplication.R
import com.example.myapplication.db.models.CategoryModel
import com.example.myapplication.db.models.TrifleModel

const val NUMBER_MAX_CHARACTERS_NAME = 30

enum class TrifleScreens() {
    Start,
    ShoppingList,
    Totals,
    AddExpense,
    EditExpense,
    SortingConfig,
    CategoriesFilter,
}

val categories = listOf(
    R.string.trifles,
    R.string.clothes,
    R.string.food,
    R.string.stationery,
    R.string.home
)

val categoriesModelMockUp = listOf(
    CategoryModel(name = "Chuminadas"),
    CategoryModel(name = "Ropa"),
    CategoryModel(name = "Comida"),
    CategoryModel(name = "Chuminadas"),
    CategoryModel(name = "Papelería"),
    CategoryModel(name = "Hogar"),
)

val categoryIcons = listOf(
    R.drawable.ic_triffle,
    R.drawable.ic_clothes,
    R.drawable.ic_food,
    R.drawable.ic_stationery,
    R.drawable.ic_home,
)

val itemsMockUpList = listOf<TrifleModel>(
    TrifleModel(name = "Moldes sandwiches", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Toalla de Manos", storeName = "Daiso", category = "Hogar", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Item Prueba", storeName = "Donki", category = "Hogar", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Tote Bag", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Tote Bag", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Camiseta", storeName = "Donki", category = "Ropa", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Portaminas", storeName = "Donki", category = "Papelería", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Sujetador", storeName = "Donki", category = "Ropa", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Pegatinas", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61"),
    TrifleModel(name = "Monedero", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61")
)

//Preferences
val PREFERENCES_YEN_EXCHANGE = "YEN_EXCHANGE"
val PREFERENCES_EUR_EXCHANGE = "EUR_EXCHANGE"
val PREFERENCES_IS_EUR_TO_YEN = "IS_EUR_TO_YEN"
val PREFERENCE_FILE = "PREFERENCE_FILE"

//SORTING
enum class SortRadioOptions(val textId: Int) {
    NameAZ(R.string.name_AZ),
    NameZA(R.string.name_ZA),
    StoreNameAZ(R.string.store_name_AZ),
    StoreNameZA(R.string.store_name_ZA),
    CreationDateNewFirst(R.string.date_of_creation_ASC),
    CreationDateOldFirst(R.string.date_of_creation_DESC),
    ModDateNewFirst(R.string.date_of_mod_ASC),
    ModDateOldFirst(R.string.date_of_mod_DESC),
}

val customRadioOptions = listOf(
    SortRadioOptions.NameAZ,
    SortRadioOptions.NameZA,
    SortRadioOptions.StoreNameAZ,
    SortRadioOptions.StoreNameZA,
    SortRadioOptions.CreationDateNewFirst,
    SortRadioOptions.CreationDateOldFirst,
    SortRadioOptions.ModDateNewFirst,
    SortRadioOptions.ModDateOldFirst,
)

//DB
const val DB_NAME = "trifle_database.db"

