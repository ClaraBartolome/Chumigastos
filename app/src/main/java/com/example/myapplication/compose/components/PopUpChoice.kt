package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun PopUpChoice(onClickEdit: () -> Unit = {},onClickDelete: () -> Unit = {},onDismissRequest: () -> Unit = {}  ) {
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),modifier = Modifier.width(224.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            PopUpChoiceItem(vector = Icons.Filled.Edit, label = stringResource(id = R.string.edit), onClick = { onClickEdit.invoke()})
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            PopUpChoiceItem(vector = Icons.Outlined.Delete, label = stringResource(id = R.string.delete), color = MaterialTheme.colorScheme.error, onClick = { onClickDelete.invoke()})
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun  PopUpChoiceItem(vector: ImageVector = Icons.Filled.Edit, color: Color = MaterialTheme.colorScheme.onSurface, label: String = "Editar", onClick: ()-> Unit = {}){
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
            .padding(horizontal = 32.dp)) {
        Image(imageVector = vector, contentDescription = "", colorFilter = ColorFilter.tint(color))
        Text(
            text = label,
            modifier = Modifier
                .padding(start = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}

@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        PopUpChoice()
    }
}