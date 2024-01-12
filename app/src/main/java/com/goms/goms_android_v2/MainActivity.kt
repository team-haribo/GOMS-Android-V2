package com.goms.goms_android_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.goms.design_system.theme.GomsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GomsTheme { colors, typography ->
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colors.BLACK),
                ) {
                    Greeting("Android", Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    GomsTheme { colors, typography ->
        Text(
            text = "Hello $name!",
            modifier = modifier,
            color = colors.WHITE
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GomsTheme { colors, typography ->
        Greeting("Android")
    }
}