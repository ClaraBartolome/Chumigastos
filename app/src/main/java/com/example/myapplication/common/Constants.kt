package com.example.myapplication.common

import com.example.myapplication.R

enum class ChumiScreens() {
    Start,
    ShoppingList,
    List
}

val categories = listOf(
    R.string.clothes,
    R.string.food,
    R.string.stationery,
    R.string.home,
    R.string.triffles
)

//Preferences
val PREFERENCES_YEN_EXCHANGE = "YEN_EXCHANGE"
val PREFERENCES_EUR_EXCHANGE = "EUR_EXCHANGE"
val PREFERENCE_FILE = "PREFERENCE_FILE"