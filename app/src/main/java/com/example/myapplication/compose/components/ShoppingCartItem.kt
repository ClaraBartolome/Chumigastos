package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.common.categories
import com.example.myapplication.common.categoryIcons
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily
import java.util.Locale

@Composable
fun ShoppingCartItem(categoryId: Int = R.drawable.ic_triffle, itemName: String = stringResource(id = R.string.trifle), yenPrice: String = "10", eurPrice: String = "10") {
    Card(colors = CardDefaults.cardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer
    ),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { /*onClick.invoke()*/ }) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(all = 8.dp)){
            Image(painter = painterResource(id = categoryId)
                , contentDescription = ""
                , modifier = Modifier.size(32.dp)
            , colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer))

            Column(modifier = Modifier
                .fillMaxHeight()) {
                Text(
                    text = itemName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.price_slash_price, formatTextCurrency(
                        parseValue(eurPrice), Locale("es", "ES")), formatTextCurrency(parseValue(yenPrice), Locale.JAPAN)),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
        }
    }
}

@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        LazyColumn(){
            items(10){
                Spacer(modifier = Modifier.height(8.dp))
                ShoppingCartItem()
            }
        }
    }
}