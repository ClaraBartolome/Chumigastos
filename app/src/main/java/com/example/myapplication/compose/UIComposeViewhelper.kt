package com.example.myapplication.compose

import android.content.Context
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import com.example.myapplication.TrifleApplicationViewModel
import com.example.myapplication.common.PREFERENCES_EUR_EXCHANGE
import com.example.myapplication.common.PREFERENCES_IS_EUR_TO_YEN
import com.example.myapplication.common.PREFERENCES_YEN_EXCHANGE
import com.example.myapplication.common.PREFERENCE_FILE
import com.example.myapplication.common.SortRadioOptions
import com.example.myapplication.db.models.TrifleModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Currency
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
        PREFERENCES_EUR_EXCHANGE, 1 / yenExchange.value
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
        PREFERENCES_YEN_EXCHANGE, 1 / eurExchange.value
    ).apply()
}

fun getIsEurToYen(activity: ComponentActivity): Boolean {
    return activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        .getBoolean(PREFERENCES_IS_EUR_TO_YEN, true)
}

fun setIsEurToYen(activity: ComponentActivity, isEurToYen: MutableState<Boolean>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putBoolean(
        PREFERENCES_IS_EUR_TO_YEN, isEurToYen.value
    ).apply()
}

fun formatText(value: Float, format: String = "%.2f"): String {
    return if (value % 1.0f == 0.0f) {
        value.toInt().toString()
    } else {
        String.format(Locale.getDefault(), format, value)
    }
}

fun formatTextCurrency(value: Float, locale: Locale): String {
    val customFormat = NumberFormat.getCurrencyInstance()
    customFormat.currency = Currency.getInstance(locale)
    return if (value % 1.0f == 0.0f) {
        customFormat.format(value.toInt())
    } else {
        customFormat.format(value)
    }
}

fun getDate(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy | HH:mm:ss", Locale.getDefault())
    return formatter.format(time)
}

fun parseValue(text: String): Float {
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

fun onCalculateExchange(
    isEurToYen: MutableState<Boolean>,
    eurValue: MutableState<Float>,
    yenValue: MutableState<Float>,
    textEur: MutableState<String>,
    textYen: MutableState<String>,
    eurExchange: MutableState<Float>,
    yenExchange: MutableState<Float>
) {
    if (isEurToYen.value) {
        eurValue.value = parseValue(textEur.value)
        yenValue.value = onUpdateCurrency(eurValue, eurExchange)
        textYen.value = formatText(yenValue.value)
        textEur.value = formatText(eurValue.value)
    } else {
        yenValue.value = parseValue(textYen.value)
        eurValue.value = onUpdateCurrency(yenValue, yenExchange)
        textYen.value = formatText(yenValue.value)
        textEur.value = formatText(eurValue.value)
    }
}

private fun onUpdateCurrency(yen: MutableState<Float>, yenExchange: MutableState<Float>) =
    yen.value * yenExchange.value

fun sortedTrifleList(trifles: List<TrifleModel>?, searchText: String): List<TrifleModel> {
    trifles?.let {
        return it.filter { linkModel ->
            linkModel.name.lowercase().contains(searchText.lowercase())
        }.sortedBy { it.name }
    } ?: run {
        return listOf()
    }
}

//ROOM

@Composable
fun SortTree(option: SortRadioOptions, viewModel: TrifleApplicationViewModel) {
    when (option) {
        SortRadioOptions.NameAZ -> {
            GetAllTriflesNameAsc(trifleViewModel = viewModel)
        }

        SortRadioOptions.NameZA -> {
            GetAllTriflesNameDesc(trifleViewModel = viewModel)
        }

        SortRadioOptions.StoreNameAZ -> {
            GetAllTriflesStoreNameAsc(trifleViewModel = viewModel)
        }

        SortRadioOptions.StoreNameZA -> {
            GetAllTriflesStoreNameDesc(trifleViewModel = viewModel)
        }

        SortRadioOptions.CreationDateNewFirst -> {
            GetAllTriflesDateOfCreationAsc(trifleViewModel = viewModel)
        }

        SortRadioOptions.CreationDateOldFirst -> {
            GetAllTriflesDateOfCreationDesc(trifleViewModel = viewModel)
        }

        SortRadioOptions.ModDateNewFirst -> {
            GetAllTriflesDateOfModDesc(trifleViewModel = viewModel)
        }

        SortRadioOptions.ModDateOldFirst -> {
            GetAllTriflesDateOfModAsc(trifleViewModel = viewModel)
        }
    }
}

@Composable
fun GetAllTriflesDateOfCreationDesc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesDateOfCreationDesc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesDateOfCreationAsc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesDateOfCreationAsc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesDateOfModDesc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesDateOfModDesc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesDateOfModAsc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesDateOfModAsc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesNameDesc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesNameDesc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesNameAsc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesNameAsc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesStoreNameDesc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesStoreNameDesc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun GetAllTriflesStoreNameAsc(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION") {
    LaunchedEffect(Unit) {
        trifleViewModel.getAllTriflesStoreNameAsc()
        Log.i(TAG, "DB created size: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun AddTrifle(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION", trifle: TrifleModel) {
    LaunchedEffect(Unit) {
        trifleViewModel.insert(trifle)
        Log.i(TAG, "Added trifle: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun AddAllTrifles(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION", trifleList: List<TrifleModel>) {
    LaunchedEffect(Unit) {
        trifleViewModel.insertAll(trifleList)
        Log.i(TAG, "Added trifle list: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun DeleteTrifle(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION", trifle: TrifleModel) {
    LaunchedEffect(Unit) {
        trifleViewModel.deleteTrifle(trifle)
        Log.i(TAG, "Added trifle list: ${trifleViewModel.allTrifles.value?.size}")
    }
}

@Composable
fun UpdateTrifle(trifleViewModel: TrifleApplicationViewModel, TAG: String = "TRIFLE_APPLICATION", trifle: TrifleModel, sortRadioOption: SortRadioOptions) {
    LaunchedEffect(Unit) {
        trifleViewModel.updateTrifle(trifle)
        Log.i(TAG, "Added trifle list: ${trifleViewModel.allTrifles.value?.size}")
    }
    SortTree(option = sortRadioOption, viewModel =trifleViewModel)
}