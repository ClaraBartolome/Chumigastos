package com.example.myapplication.common

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R

enum class TriffleScreens() {
    Start,
    ShoppingList,
    Totals,
    AddExpense
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
val PREFERENCES_IS_EUR_TO_YEN = "IS_EUR_TO_YEN"
val PREFERENCE_FILE = "PREFERENCE_FILE"

//DB
const val DB_NAME = "triffle_database.db"

