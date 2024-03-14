package com.jasmeet.chatwave.appComponent

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jasmeet.chatwave.ui.theme.inter

/**
* Created by Jasmeet Singh
* on 13 March 2024.
*/


@Composable
fun RowScope.CustomTab(
    text: String,
    isSelected: Boolean,
    index: Int,
    onTabClick: (Int) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            Color.Black
        } else {
            Color(0xff808080)
        },
        animationSpec = tween(500, 0, LinearEasing),
        label = "Text Color",
    )

    Box(
        modifier = Modifier
            .weight(1f)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onTabClick.invoke(index) },
        contentAlignment = Alignment.Center
    ) {

        androidx.compose.animation.AnimatedVisibility(
            visible = isSelected,
            enter = slideInHorizontally(
                initialOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)
            ),
            exit = slideOutHorizontally(
                targetOffsetX = {
                    if (index == 0) {
                        it
                    } else {
                        -it
                    }
                },
                animationSpec = tween(500, 0, LinearEasing)

            )

        ) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 8.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
            ) {}
        }

        TextComponent(
            text = text,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            textColor = tabTextColor,
            modifier = Modifier.padding(vertical = 8.dp),
            fontFamily = inter

        )
    }
}