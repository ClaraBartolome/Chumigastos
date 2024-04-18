package com.example.myapplication.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.myapplication.R
import com.example.myapplication.common.SortRadioOptions
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.common.customRadioOptions
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun DropDownMenu(
    isOpen: MutableState<Boolean> = remember { mutableStateOf(true) },
    orderChosen: MutableState<SortRadioOptions> = remember { mutableStateOf(customRadioOptions[0]) },
    onCategoryClick: () -> Unit = {},
    onOrderClick: () -> Unit = {},) {
    Box(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.TopEnd)){
        DropdownMenu(expanded = isOpen.value, onDismissRequest = { isOpen.value = false }) {
            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = R.string.filter_by_categories),
                    style = MaterialTheme.typography.titleSmall,
                )
            }, onClick = { onCategoryClick.invoke() })
            HorizontalDivider()
            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = R.string.order_by_arg, stringResource(id = orderChosen.value.textId)),
                    style = MaterialTheme.typography.titleSmall,
                )
            }, onClick = { onOrderClick.invoke() })
        }
    }
}

@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        Surface {
            DropDownMenu()
        }
    }
}