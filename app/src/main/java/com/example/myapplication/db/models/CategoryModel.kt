package com.example.myapplication.db.models

import com.example.myapplication.R

data class CategoryModel(
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
    var position = when(name){
        "Chuminadas", "Trifles" -> 0
        "Clothes", "Ropa" -> 1
        "Food", "Comida" -> 2
        "Stationery", "Papelería" -> 3
        "Home", "Hogar" -> 4
        else -> 0
    }
}