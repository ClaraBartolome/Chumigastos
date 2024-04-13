package com.example.myapplication.compose

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import com.example.myapplication.common.PREFERENCES_EUR_EXCHANGE
import com.example.myapplication.common.PREFERENCES_YEN_EXCHANGE
import com.example.myapplication.common.PREFERENCE_FILE

fun getYenExchange(activity: ComponentActivity): Float {
    return activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        .getFloat(PREFERENCES_YEN_EXCHANGE, 0.0061f)
}

fun setYenExchange(activity: ComponentActivity, yenExchange: MutableState<Float>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putFloat(
        PREFERENCES_YEN_EXCHANGE, yenExchange.value
    ).apply()
}

fun getEurExchange(activity: ComponentActivity): Float {
    return activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        .getFloat(PREFERENCES_EUR_EXCHANGE, 163.57f)
}

fun setEurExchange(activity: ComponentActivity, eurExchange: MutableState<Float>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putFloat(
        PREFERENCES_EUR_EXCHANGE, eurExchange.value
    ).apply()
}