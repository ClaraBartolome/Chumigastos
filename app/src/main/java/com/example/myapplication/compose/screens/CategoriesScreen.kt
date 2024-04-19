package com.example.myapplication.compose.screens

import android.provider.SyncStateContract.Constants
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.common.SortRadioOptions
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.common.categories
import com.example.myapplication.common.categoriesModelMockUp
import com.example.myapplication.common.customRadioOptions
import com.example.myapplication.compose.components.CategoryItem
import com.example.myapplication.db.models.CategoryModel
import com.example.myapplication.db.models.TrifleModel
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.poppinsFontFamily

@Composable
fun CategoriesScreen(selectedOptions: SnapshotStateList<CategoryModel> = remember { mutableStateListOf<CategoryModel>() }) {
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(Modifier.selectableGroup()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.filter_categories),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onBackground,
            fontFamily = poppinsFontFamily,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = stringResource(id = R.string.category_explanation),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(120.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(selectedOptions.size){ itemId ->
                    FilterCategoryItem(
                        labelId = categories[selectedOptions[itemId].position],
                        categoryId = selectedOptions[itemId].iconId,
                        isSelected = selectedOptions[itemId].isSelected) {
                        selectedOptions[itemId] = selectedOptions[itemId].copy(isSelected = !selectedOptions[itemId].isSelected)
                    }
                }
            }, modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
    }
}

@Composable
private fun FilterCategoryItem(labelId: Int, categoryId: Int, isSelected: Boolean, onClick: () -> Unit ){
    Card(colors = CardDefaults.cardColors(
        containerColor = if (isSelected) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
    ),
        modifier = Modifier.clickable { onClick.invoke() }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(painter = painterResource(id = categoryId)
                , contentDescription = ""
                , modifier = Modifier.size(32.dp).padding(top = 8.dp)
                , colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer))

            Text(
                text = stringResource(id = labelId),
                style = MaterialTheme.typography.labelMedium,
                color = if (isSelected) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}

@ThemePreviews
@Composable
private fun prevShoppingListScreen() {
    MyApplicationTheme {
        Surface {
            val testList = remember { mutableStateListOf<CategoryModel>() }
            testList.addAll(categoriesModelMockUp)
            CategoriesScreen(testList)
        }
    }
}
