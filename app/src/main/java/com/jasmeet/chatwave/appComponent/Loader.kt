package com.jasmeet.chatwave.appComponent

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.RenderMode
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


@Composable
fun Loader(
    rawRes : Int,
    modifier: Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(rawRes),
        cacheKey = "loader"
    )
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )


    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier,
        renderMode = RenderMode.HARDWARE,
        )
}

