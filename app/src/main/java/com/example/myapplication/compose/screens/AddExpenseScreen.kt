package com.example.myapplication.compose.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.common.ThemeScreenSizePrev
import com.example.myapplication.common.categories
import com.example.myapplication.compose.components.CategoryItem
import com.example.myapplication.compose.components.CustomTextField
import com.example.myapplication.compose.components.MainScreenChangeComponent
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.getDate
import com.example.myapplication.compose.onCalculateExchange
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily

@Composable
fun AddExpenseScreen(
    eurValue: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    yenValue: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    yenExchange: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    eurExchange: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    storeName: MutableState<String> = remember { mutableStateOf("") },
    onConfirmation: @Composable (TrifleModel) -> Unit = {}
) {
    val itemName = remember { mutableStateOf("") }
    val categoryNumber = remember { mutableStateOf(-1) }
    val textYen = remember { mutableStateOf(formatText(yenValue.value)) }
    val textEur = remember { mutableStateOf(formatText(eurValue.value)) }
    var isConfirmed by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface), verticalArrangement = Arrangement.Top
    ) {
        item {
            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.add_expense),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                text = itemName,
                placeholder = stringResource(id = R.string.trifle),
                label = stringResource(id = R.string.trifle_name)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                text = storeName,
                placeholder = stringResource(id = R.string.acme),
                label = stringResource(id = R.string.store_name)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(id = R.string.category),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(horizontal = 16.dp),
            )
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(120.dp),
                verticalItemSpacing = 4.dp,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    items(categories.size) { itemId ->
                        CategoryItem(
                            categories[itemId],
                            isSelected = categoryNumber.value == itemId
                        ) { categoryNumber.value = itemId }
                    }
                }, modifier = Modifier
                    .height(125.dp)
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.price),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier,
                )

                Text(
                    text = stringResource(id = R.string.calculate),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .clickable {
                            onCalculateExchange(
                                isEurToYen,
                                eurValue,
                                yenValue,
                                textEur,
                                textYen,
                                eurExchange,
                                yenExchange
                            )
                        },
                )
            }

            Column(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                MainScreenChangeComponent(
                    isTopCard = true,
                    text = if (isEurToYen.value) textEur else textYen,
                    isTextFieldEnabled = true,
                    textFieldWeight = 2.0f,
                    currencyName = stringResource(id = if (isEurToYen.value) R.string.eur else R.string.yen),
                    imageFlag = if (isEurToYen.value) R.drawable.image_eur_flag else R.drawable.image_japan_flag
                )
                Spacer(modifier = Modifier.height(16.dp))
                MainScreenChangeComponent(
                    isTopCard = false,
                    isTextFieldEnabled = false,
                    currencyName = stringResource(id = if (isEurToYen.value) R.string.yen else R.string.eur),
                    imageFlag = if (isEurToYen.value) R.drawable.image_japan_flag else R.drawable.image_eur_flag,
                    text = if (isEurToYen.value) textYen else textEur,
                    firstExchange = stringResource(
                        id = R.string.JPY_exchange_main,
                        formatText(yenExchange.value, "%.4f")
                    ),
                    secondExchange = stringResource(
                        id = R.string.Eur_exchange_main,
                        formatText(eurExchange.value)
                    )
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter, modifier = Modifier.fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
        ) {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 2.dp,
                    focusedElevation = 2.dp,
                    hoveredElevation = 2.dp
                ),
                modifier = Modifier.padding(vertical = 16.dp),
                onClick = {
                    isConfirmed = true
                }) {
                Text(
                    text = stringResource(id = R.string.add_purchase),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            if (isConfirmed) {
                isConfirmed = false
                onConfirmation.invoke(
                    TrifleModel(
                        name = itemName.value.ifBlank { stringResource(id = R.string.trifle) },
                        storeName = storeName.value.ifBlank { stringResource(id = R.string.acme) },
                        dateOfCreation = getDate(),
                        category = if (categoryNumber.value != -1) stringResource(id = categories[categoryNumber.value]) else stringResource(
                            id = R.string.trifles
                        ),
                        yenPrice = formatText(yenValue.value),
                        eurPrice = formatText(eurValue.value)
                    )
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        AddExpenseScreen()
    }
}