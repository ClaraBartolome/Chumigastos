package com.example.myapplication.compose.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

@Composable
fun MainScreen() {
    Text(
        text = "Hello Android"
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun prevMainScreen(){
    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            MainScreen()
        }
    }
}