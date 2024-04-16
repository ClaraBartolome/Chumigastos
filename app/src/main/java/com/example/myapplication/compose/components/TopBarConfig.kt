package com.example.myapplication.compose.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.myapplication.R
import com.example.myapplication.common.TriffleScreens
import com.example.myapplication.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarDefault(
    navController: NavHostController,
    screen: TriffleScreens,
    isAlertExchangeOpen: MutableState<Boolean>,
    onNavigationIconClick: () -> Unit = {}
) {
    val showMenu = remember { mutableStateOf(false) }
    TopAppBar(
        title = { TitleText(screen) },
        actions = {
            when (screen) {
                TriffleScreens.Start, TriffleScreens.ShoppingList, TriffleScreens.Totals, TriffleScreens.AddExpense -> {}
            }
        },
        navigationIcon = {
            if (screen == TriffleScreens.Start) {

            }
            when (screen) {
                TriffleScreens.Start -> {
                    IconButtonApp(
                        imageVector = Icons.Filled.Menu,
                        action = { onNavigationIconClick.invoke() })
                }
                TriffleScreens.ShoppingList, TriffleScreens.Totals, TriffleScreens.AddExpense -> {
                    IconButtonApp(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        action = { navController.popBackStack() })
                }
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
private fun IconButtonApp(
    imageVector: ImageVector,
    action: () -> (Unit),
    contentDescription: String = ""
) {
    IconButton(onClick = action) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun TitleText(screen: TriffleScreens) {
    val title = when (screen) {
        TriffleScreens.Start -> stringResource(id = R.string.app_name)
        else -> stringResource(id = R.string.app_name)
    }
    Text(
        text = title,
        color = MaterialTheme.colorScheme.onPrimary,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily
    )
}