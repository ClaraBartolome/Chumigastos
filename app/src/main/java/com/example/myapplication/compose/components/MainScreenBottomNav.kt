package com.example.myapplication.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.MyApplicationTheme


//HEIGHT ES DE 80.DP
@Composable
fun MainScreenBottomNav(
    onClickEdit: () -> Unit = {},
    onClickAdd: () -> Unit = {},
    onClickChange: () -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {


        IconButtonApp(Icons.Filled.Edit, stringResource(id = R.string.edit_change), onClickEdit)

        FloatingActionButton(
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                defaultElevation = 2.dp,
                pressedElevation = 2.dp,
                focusedElevation = 2.dp,
                hoveredElevation = 2.dp
            ),
            onClick = { onClickAdd.invoke() }) {
            Icon(Icons.Filled.Add, "Localized description")
        }

        IconButtonApp(
            imageId = R.drawable.ic_money_exchange,
            label = stringResource(id = R.string.swap_currencies),
            onClickChange
        )
    }
}

@Composable
fun MainScreenBottomNavOverlap() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 10.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .matchParentSize()
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = (Icons.Filled.Edit),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }


            }
        }

        Box {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                FloatingActionButton(
                    shape = CircleShape,
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                        defaultElevation = 2.dp,
                        pressedElevation = 2.dp,
                        focusedElevation = 2.dp,
                        hoveredElevation = 2.dp
                    ),
                    onClick = { }) {
                    Icon(Icons.Filled.Add, "")
                }
            }
        }

    }
}

@Composable
private fun IconButtonApp(imageVector: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                imageVector = imageVector,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun IconButtonApp(imageId: Int, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(onClick = { onClick.invoke() }) {
            Icon(
                painterResource(id = imageId),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 2
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, apiLevel = 33, locale = "es")
@Composable
private fun prevMainScreen() {
    MyApplicationTheme {
        Scaffold(
            bottomBar = { MainScreenBottomNav() }
        ) { innerPadding ->
            Column(Modifier.padding(innerPadding)) {

            }
        }
    }
}