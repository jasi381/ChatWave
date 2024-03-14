package com.jasmeet.chatwave.appComponent

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.chatwave.ui.theme.inter

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    textColor: Color = Color.Black,
    textSize: TextUnit = 16.sp,
    fontFamily:FontFamily = inter,
    fontWeight: FontWeight = FontWeight.Normal,
    buttonColor: Color =  Color(0xffff864a),
    shape: Shape = RectangleShape,
    enabled: Boolean = true,
    elevation: ButtonElevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    disabledColor: Color = Color(0xffffaf86)
) {

    Button(
        modifier = modifier,
        onClick = { onClick.invoke() },
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            disabledContainerColor = disabledColor
        ),
        shape = shape,
        enabled = enabled,
        elevation = elevation
    ) {
        TextComponent(
            text = text,
            fontFamily = fontFamily,
            fontWeight = fontWeight,
            textSize = textSize,
            textColor = textColor,
            modifier = textModifier
        )
    }

}