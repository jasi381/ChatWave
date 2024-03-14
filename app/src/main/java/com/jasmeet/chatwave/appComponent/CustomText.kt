package com.jasmeet.chatwave.appComponent

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.chatwave.ui.theme.inter


/**
* Created by Jasmeet Singh
* on 13 March 2024.
*/

@Composable
fun TextComponent(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.Black,
    textSize: TextUnit = 16.sp,
    fontFamily: FontFamily = inter,
    fontWeight: FontWeight = FontWeight.Normal,
    textAlign: TextAlign = TextAlign.Start,
    lineHeight: TextUnit = 24.sp,
) {
    Text(
        text = text,
        modifier = modifier,
        style = TextStyle(
            fontSize = textSize,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            color = textColor,
            textAlign = textAlign,
            lineHeight = lineHeight
        )
    )

}

@Preview
@Composable
private fun TextComponentPreview() {

    TextComponent(text = "Hello", modifier = Modifier.padding(10.dp))
}