package com.jasmeet.chatwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jasmeet.chatwave.navigation.MainNavHost
import com.jasmeet.chatwave.ui.theme.LoginChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginChallengeTheme {
                MainNavHost()
            }
        }
    }
}
