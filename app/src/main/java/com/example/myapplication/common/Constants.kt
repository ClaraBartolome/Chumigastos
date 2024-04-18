package com.example.myapplication.common

import com.example.myapplication.R
import com.example.myapplication.db.models.TrifleModel

enum class TrifleScreens() {
    Start,
    ShoppingList,
    Totals,
    AddExpense,
    EditExpense
}

val categories = listOf(
    R.string.trifles,
    R.string.clothes,
    R.string.food,
    R.string.stationery,
    R.string.home
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

//DB
const val DB_NAME = "trifle_database.db"

