package com.jasmeet.chatwave.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jasmeet.chatwave.screens.PHONE_NUMBER
import com.jasmeet.chatwave.screens.login.LoginScreen
import com.jasmeet.chatwave.screens.Screens
import com.jasmeet.chatwave.screens.VERIFICATION_ID
import com.jasmeet.chatwave.screens.login.OtpVerificationScreen

/**
* Created by Jasmeet Singh
* on 13 March 2024.
*/

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun MainNavHost() {

    val navHost = rememberNavController()

    NavHost(
        navController = navHost,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(route = Screens.LoginScreen.route) {
            LoginScreen(navHost = navHost)
        }

        composable(
            Screens.OtpScreen.route,
            arguments =  listOf(
                navArgument(VERIFICATION_ID){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },
                navArgument(PHONE_NUMBER){
                    type = NavType.StringType
                    defaultValue = "0"
                    nullable = true
                },

            )
        ) {
            OtpVerificationScreen(
                phoneNumber = it.arguments?.getString(PHONE_NUMBER),
                verificationId = it.arguments?.getString(VERIFICATION_ID),
                navHost = navHost
            )
        }

        composable(route = Screens.SuccessScreen.route) {
            SuccessScreen(navHost = navHost)
        }

    }


}

@Composable
fun SuccessScreen(modifier: Modifier = Modifier, navHost: NavHostController) {
    Column(Modifier.fillMaxSize().background(Color.Cyan)) {

    }

}