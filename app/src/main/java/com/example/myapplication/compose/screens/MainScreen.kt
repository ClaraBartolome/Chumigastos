package com.example.myapplication.compose.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.compose.components.MainScreenChangeComponent
import com.example.myapplication.compose.components.MainScreenTotalsComponent
import com.example.myapplication.compose.formatText
import com.example.myapplication.compose.onCalculateExchange
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily

@Composable
fun MainScreen(
    yenExchange: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    eurExchange: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    yenValue: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    eurValue: MutableState<Float> = remember { mutableStateOf<Float>(1.0f) },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    onClickAdd: () -> Unit = {},
    onClickList: () -> Unit = {},
    onClickTotals: () -> Unit = {}
) {

    val textYen = remember { mutableStateOf(formatText(yenValue.value)) }
    val textEur = remember { mutableStateOf(formatText(eurValue.value)) }
    Column(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top, modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.currency_exchange),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = poppinsFontFamily
                )

                Text(text = stringResource(id = R.string.calculate),
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        onCalculateExchange(
                            isEurToYen,
                            eurValue,
                            yenValue,
                            textEur,
                            textYen,
                            eurExchange,
                            yenExchange
                        )
                    })
            }
            Column(Modifier.padding(vertical = 16.dp)) {
                MainScreenChangeComponent(
                    isTopCard = true,
                    text = if (isEurToYen.value) textEur else textYen,
                    isTextFieldEnabled = true,
                    textFieldWeight = 2.0f,
                    currencyName = stringResource(id = if (isEurToYen.value) R.string.eur else R.string.yen),
                    imageFlag = if (isEurToYen.value) R.drawable.image_eur_flag else R.drawable.image_japan_flag
                )
            }
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

        HorizontalDivider(
            thickness = 2.dp,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 32.dp)
        )

        Column(Modifier.padding(horizontal = 16.dp)) {
            MainScreenTotalsComponent(onClickSeeAll = onClickTotals)
        }
    }
}


@ThemePreviews
@Composable
private fun prevMainScreen() {
    MyApplicationTheme {
        Surface() {
            MainScreen()
        }
    }
}