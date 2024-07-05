package com.goms.design_system.component.lottie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun AnimatedLottie (
    rawId: Int,
    modifier: Modifier = Modifier,
    isInfiniteRepetition: Boolean = false
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(rawId))

    val iterations = if (isInfiniteRepetition){
        LottieConstants.IterateForever
    } else {
        1
    }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = true
    )

    LottieAnimation( 
        composition = composition,
        progress = { progress },
        modifier = modifier
    )
}