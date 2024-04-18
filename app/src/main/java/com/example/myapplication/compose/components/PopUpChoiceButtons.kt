package com.example.myapplication.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.R
import com.example.myapplication.common.ThemePreviews
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun PopUpChoiceButtons(
    labelText: String = stringResource(id = R.string.delete_popup),
    onClickAccept: () -> Unit = {},
    onClickDismiss: () -> Unit = {},
    onDismissRequest: () -> Unit = {} ) {
    Dialog(onDismissRequest = { onDismissRequest.invoke() }) {
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),modifier = Modifier.width(290.dp)) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = labelText,
                    modifier = Modifier
                        .padding(horizontal = 32.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)) {
                    Column (Modifier.weight(1f)) {
                        PopUpButton(label = stringResource(id = R.string.no),
                            textWeight = FontWeight.SemiBold) {
                            onClickDismiss.invoke()
                        }
                    }
                    Column (Modifier.weight(1f)) {
                        PopUpButton(label = stringResource(id = R.string.yes),
                            containerColor = MaterialTheme.colorScheme.error,
                            textColor = MaterialTheme.colorScheme.onSurface,
                            textWeight = FontWeight.SemiBold) {
                            onClickAccept.invoke()
                        }
                    }

                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}



@ThemePreviews
@Composable
private fun prevAddExpenseScreen() {
    MyApplicationTheme {
        PopUpChoiceButtons()
    }
}