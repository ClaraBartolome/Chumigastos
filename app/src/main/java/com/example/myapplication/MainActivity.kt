package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.myapplication.compose.getEurExchange
import com.example.myapplication.compose.getIsEurToYen
import com.example.myapplication.compose.getYenExchange
import com.example.myapplication.compose.screens.UICompose
import com.example.myapplication.compose.setEurExchange
import com.example.myapplication.compose.setIsEurToYen
import com.example.myapplication.compose.setYenExchange
import com.example.myapplication.db.TrifleApplication
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val trifleApplicationViewModel: TrifleApplicationViewModel by viewModels {
        TrifleViewModelFactory((applicationContext as TrifleApplication).repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val yenExchange = remember { mutableStateOf<Float>(getYenExchange(this)) }
                val eurExchange = remember { mutableStateOf<Float>(getEurExchange(this)) }
                val isEurToYen = remember { mutableStateOf(getIsEurToYen(this)) }
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    UICompose(
                        yenExchange,
                        eurExchange,
                        isEurToYen,
                        trifleApplicationViewModel,
                        { setYenExchange(this, yenExchange) },
                        { setEurExchange(this, eurExchange) },
                        { setIsEurToYen(this, isEurToYen) }
                    )
                }
            }
        }
    }
}