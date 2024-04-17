package com.example.myapplication.db.models

import androidx.compose.ui.res.stringResource
import com.example.myapplication.R

data class Category(
    val name:String,
) {
    var iconId = when(name){
        "Chuminadas", "Trifles" -> R.drawable.ic_triffle
        "Clothes", "Ropa" -> R.drawable.ic_clothes
        "Home", "Hogar" -> R.drawable.ic_home
        "Food", "Comida" -> R.drawable.ic_food
        "Stationery", "Papelería" -> R.drawable.ic_stationery
        else -> R.drawable.ic_triffle
    }
}