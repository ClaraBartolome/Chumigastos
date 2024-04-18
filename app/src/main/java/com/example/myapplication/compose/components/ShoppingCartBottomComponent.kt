package com.example.myapplication.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily
import java.util.Locale

@Composable
fun ShoppingCartBottomComponent(
    itemsList: List<TrifleModel> = remember { mutableStateListOf() },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    onBuyClick: () -> Unit = {}
) {
    val textYen = formatTextCurrency(calculateYenTotal(itemsList), Locale.JAPAN)
    val textEur = formatTextCurrency(calculateEurTotal(itemsList), Locale("es", "ES"))
    Text(
        text = stringResource(id = R.string.total_items, itemsList.size),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
    )
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Column(modifier = Modifier
            .fillMaxHeight()) {
            Text(
                text = if(isEurToYen.value) textEur else textYen,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = if(isEurToYen.value) textYen else textEur,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onTertiary,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
        }


        Button(
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = {
                onBuyClick.invoke()
            }) {
            Text(
                text = stringResource(id = R.string.buy_everything),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                textAlign = TextAlign.Center,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .widthIn(min = 60.dp)
            )
        }
    }
}

@Composable
fun TotalsBottomComponent(
    itemsList: List<TrifleModel> = remember { mutableStateListOf() },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
) {
    val textYen = formatTextCurrency(calculateYenTotal(itemsList), Locale.JAPAN)
    val textEur = formatTextCurrency(calculateEurTotal(itemsList), Locale("es", "ES"))
    Text(
        text = stringResource(id = R.string.trifles_purchased_in_total, itemsList.size),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onTertiary,
        fontWeight = FontWeight.SemiBold,
        fontFamily = poppinsFontFamily,
        modifier = Modifier.padding(start = 16.dp, top = 8.dp)
    )
    Row(verticalAlignment = Alignment.Top, horizontalArrangement = Arrangement.Start, modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()){
        Text(
            text = stringResource(id = R.string.price_slash_price,
                if(isEurToYen.value) textEur else textYen,
                if(isEurToYen.value) textYen else textEur),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp)
        )
    }
}

private fun calculateYenTotal(itemsList: List<TrifleModel>): Float{
    var total = 0f
    itemsList.forEach {item ->
        total += parseValue(item.yenPrice)
    }
    return total
}

private fun calculateEurTotal(itemsList: List<TrifleModel>): Float{
    var total = 0f
    itemsList.forEach {item ->
        total += parseValue(item.eurPrice)
    }
    return total
}

/*@ThemePreviews
@Composable
private fun prevShoppingListBottomScreen() {
    MyApplicationTheme {
        Surface {
            Box{
                Column(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.tertiary)) {
                    ShoppingCartBottomComponent()
                }
            }
        }
    }
}*/

@ThemePreviews
@Composable
private fun prevTotalsBottomScreen() {
    MyApplicationTheme {
        Surface {
            Box{
                Column(modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.tertiary)) {
                    TotalsBottomComponent()
                }
            }
        }
    }
}