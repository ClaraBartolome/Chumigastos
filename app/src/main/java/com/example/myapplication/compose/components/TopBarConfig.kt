package com.example.myapplication.compose.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.common.ChumiScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefault(
    navController: NavHostController,
    screen: ChumiScreens,
    isAlertExchangeOpen: MutableState<Boolean>
) {
    val showMenu = remember { mutableStateOf(false) }
    TopAppBar(
        title = { TitleText(screen) },
        actions = {
            when (screen) {
                ChumiScreens.Start -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_dots,
                        action = { showMenu.value = !showMenu.value })
                    DropdownMenu(expanded = showMenu.value, onDismissRequest = { showMenu.value = false }) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = stringResource(id = R.string.edit_change),
                                    color = MaterialTheme.colorScheme.onBackground,
                                )
                            },
                            onClick = {
                                isAlertExchangeOpen.value = true
                                showMenu.value = false},
                            leadingIcon = {
                                Icon(painter = painterResource(R.drawable.ic_edit),
                                    contentDescription = "",
                                    tint = MaterialTheme.colorScheme.onBackground)
                            }
                        )
                    }
                }
                ChumiScreens.ShoppingList, ChumiScreens.Totals -> {}
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun IconButtonApp(iconId: Int, action: () -> (Unit), contentDescription: String = "") {
    IconButton(onClick = action) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun TitleText(screen: ChumiScreens) {
    val title = when (screen) {
        ChumiScreens.Start -> stringResource(id = R.string.app_name)
        else -> stringResource(id = R.string.app_name)
    }
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary
    )
}