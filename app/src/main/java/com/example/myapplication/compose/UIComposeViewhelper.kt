package com.example.myapplication.compose

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.runtime.MutableState
import com.example.myapplication.common.PREFERENCES_EUR_EXCHANGE
import com.example.myapplication.common.PREFERENCES_YEN_EXCHANGE
import com.example.myapplication.common.PREFERENCE_FILE
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getYenExchange(activity: ComponentActivity): Float {
    return activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        .getFloat(PREFERENCES_YEN_EXCHANGE, 0.0061f)
}

fun setYenExchange(activity: ComponentActivity, yenExchange: MutableState<Float>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putFloat(
        PREFERENCES_YEN_EXCHANGE, yenExchange.value
    ).apply()
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putFloat(
        PREFERENCES_EUR_EXCHANGE, 1/yenExchange.value
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
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putFloat(
        PREFERENCES_YEN_EXCHANGE, 1/eurExchange.value
    ).apply()
}

fun formatText(value: Float): String {
    return if (value % 1.0f == 0.0f) {
        value.toInt().toString()
    } else {
        String.format(Locale.getDefault(), "%.2f", value)
    }
}

fun getDate(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(time)
}

fun parseValue(text: String): Float{
    // Encontrar la última coma en la cadena
    val lastCommaIndex = text.lastIndexOf(",")

    // Separar la parte entera de la parte decimal
    val integerPart = if (lastCommaIndex != -1) text.substring(0, lastCommaIndex) else text
    val decimalPart = if (lastCommaIndex != -1) text.substring(lastCommaIndex + 1) else ""

    // Eliminar comas y puntos solo de la parte entera
    val cleanedIntegerPart = integerPart.replace(Regex("[,.]"), "")

    // Reunir la parte entera limpia y la parte decimal
    val cleanedText = if (decimalPart.isNotEmpty()) {
        "$cleanedIntegerPart.${decimalPart.replace(".", "")}"
    } else {
        cleanedIntegerPart
    }

    // Convertir la cadena limpia a un número flotante
    return cleanedText.toFloatOrNull() ?: 0.0f
}