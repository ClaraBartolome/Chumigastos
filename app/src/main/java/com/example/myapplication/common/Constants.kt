package com.example.myapplication.common

import com.example.myapplication.R

enum class ChumiScreens() {
    Start,
    ShoppingList,
    Totals
}

val categories = listOf(
    R.string.trifles,
    R.string.clothes,
    R.string.food,
    R.string.stationery,
    R.string.home
)

//Preferences
val PREFERENCES_YEN_EXCHANGE = "YEN_EXCHANGE"
val PREFERENCES_EUR_EXCHANGE = "EUR_EXCHANGE"
val PREFERENCE_FILE = "PREFERENCE_FILE"

//DB
const val DB_NAME = "triffle_database.db"