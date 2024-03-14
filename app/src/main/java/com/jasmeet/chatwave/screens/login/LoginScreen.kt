package com.jasmeet.chatwave.screens.login

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.jasmeet.chatwave.appComponent.CustomTab
import com.jasmeet.chatwave.appComponent.TextComponent

/**
 * Created by Jasmeet Singh
 * on 23 March 2024.
 */

@Composable
fun LoginScreen(navHost: NavHostController) {

    val screenOrientation = LocalConfiguration.current.orientation

    val tabs = listOf("Mobile", "Email")
    var selectedTabIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .then(
                if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) Modifier
                    .padding(horizontal = 20.dp)
                    .navigationBarsPadding()
                     else Modifier.padding(horizontal = 22.dp)
            )
    ) {
        TextComponent(
            text = "Login Account",
            fontWeight = FontWeight.W600,
            textColor = Color.Black,
            textSize = 22.sp,
            modifier = Modifier
                .statusBarsPadding()
                .then(
                    if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) Modifier.padding(
                        top = 10.dp
                    ) else Modifier.padding(top = 18.dp)
                )

        )

        TextComponent(
            text = "Securely login to your account.",
            fontWeight = FontWeight.Normal,
            textColor = Color(0xff595959),
            textSize = 15.sp,
            modifier = Modifier.padding(top = 6.dp)
        )

        Row(
            Modifier
                .padding(top = 18.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(color = Color(0xffededed))
                .then(
                    if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) Modifier.height(64.dp) else Modifier.height(
                        LocalConfiguration.current.screenHeightDp.dp * 0.08f
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            tabs.forEachIndexed { index, tab ->
                CustomTab(
                    text = tab, selectedTabIndex == index, index
                ) {
                    selectedTabIndex = it
                }
            }

        }
        androidx.compose.animation.AnimatedVisibility (
            visible = selectedTabIndex == 0,
            enter = slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(500, 0, LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = {it },
                animationSpec = tween(500, 0, LinearEasing)

            )

        ){
           PhoneNumberUi(navHost = navHost, modifier = Modifier.padding(top = 20.dp).fillMaxWidth()){
               Log.d("LoginScreen", "LoginScreen: $it")
           }
        }
        androidx.compose.animation.AnimatedVisibility (
            visible = selectedTabIndex ==1,
            enter = slideInHorizontally(
                initialOffsetX = {-it },
                animationSpec = tween(500, 0, LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(500, 0, LinearEasing)

            )
        ){
            EmailLoginUi(navHost = navHost)
        }

    }

}

@Preview(device = Devices.PIXEL_6)
@Composable
private fun LoginScreenPreview() {

    val navHost = NavHostController(LocalContext.current)
    LoginScreen(navHost)

}








