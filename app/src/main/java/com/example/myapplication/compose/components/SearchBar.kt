package com.example.myapplication.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.NUMBER_MAX_CHARACTERS_NAME
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.common.categoryIcons
import com.example.myapplication.compose.formatTextCurrency
import com.example.myapplication.compose.parseValue
import com.example.myapplication.db.models.CategoryModel
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import java.util.Locale

@Composable
fun SearchBar(
    text: MutableState<String> = remember { mutableStateOf("") },
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    onSearchInit: () -> List<TrifleModel> = { listOf<TrifleModel>() },
    onClickOnSearched: (TrifleModel) -> Unit = {},
    onTextChange: (String) -> Unit = {},
    onCloseClicked: () -> Unit = {}
) {
    val expanded = remember { mutableStateOf(false) }
        LazyColumn() {
            item {
                SearchBarConfig(
                    text = text,
                    onTextChange = {
                        onTextChange.invoke(it)
                        expanded.value = true
                                   },
                    onCloseClicked = onCloseClicked
                )
            }
            item {
                AnimatedVisibility(visible = expanded.value) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 150.dp),
                        ) {
                            items(onSearchInit.invoke()){
                                ItemSearchSuggestion(trifle = it, onClick = { onClickOnSearched.invoke(it) })
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun SearchBarConfig(
    text: MutableState<String>,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        tonalElevation = 2.dp,
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            value = text.value,
            onValueChange = {
                onTextChange.invoke(it)
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(0.6f),
                    enabled = false,
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.value.isNotEmpty()) {
                            onTextChange.invoke("")
                        } else {
                            onCloseClicked.invoke()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 4.dp)
                .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape),
            shape = CircleShape,
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ItemSearchSuggestion(
    trifle:TrifleModel = TrifleModel(name = "Moldes sandwiches", storeName = "Donki", category = "Chuminadas", yenPrice = "100", eurPrice = "0,61"),
    isEurToYen: MutableState<Boolean> = remember { mutableStateOf(true) },
    onClick: () -> Unit = {}
) {
    val textEur = formatTextCurrency(
        parseValue(trifle.eurPrice), Locale("es", "ES")
    )
    val textYen = formatTextCurrency(parseValue(trifle.yenPrice), Locale.JAPAN)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(painter = painterResource(id = CategoryModel(trifle.category).iconId)
                , contentDescription = ""
                , modifier = Modifier
                    .size(32.dp)
                    .padding(start = 8.dp)
                , colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer))
            Column(modifier = Modifier
                .height(IntrinsicSize.Min)
                .clickable { onClick() }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        trifle.name.take(NUMBER_MAX_CHARACTERS_NAME),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(id = R.string.price_slash_price,
                            if(isEurToYen.value) textEur else textYen,
                            if(isEurToYen.value) textYen else textEur),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = trifle.storeName.take(NUMBER_MAX_CHARACTERS_NAME),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 8.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
}

/*@ThemePreviews
@Composable
private fun prevSearchBar() {
    MyApplicationTheme {
        Surface {
            SearchBar()
        }
    }
}*/
@ThemePreviews
@Composable
private fun prevItemSearchSuggestion() {
    MyApplicationTheme {
        Surface {
            ItemSearchSuggestion()
        }
    }
}