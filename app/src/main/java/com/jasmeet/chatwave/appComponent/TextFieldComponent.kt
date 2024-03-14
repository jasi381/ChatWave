package com.jasmeet.chatwave.appComponent

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jasmeet.chatwave.ui.theme.inter

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    hint : String,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardType: KeyboardType = KeyboardType.Phone,
    leadingIcon : @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    minLines: Int = 1,
    singleLine : Boolean = true,
) {
    OutlinedTextField(
        value = value ,
        onValueChange = {
            onValueChange.invoke(it)
        },
        textStyle = TextStyle(
            fontFamily = inter,
            fontSize = 15.sp,
            fontWeight = FontWeight(600),
            color = Color.Black.copy(alpha = 0.5f),
        ),
        modifier = modifier,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor =  Color(0xff999999),
            unfocusedIndicatorColor = Color(0xffd9d9d9),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            cursorColor =  Color(0xff999999),
            selectionColors = TextSelectionColors(
                handleColor = Color(0xff999999),
                backgroundColor = Color(0xffd9d9d9),
            )
        ),
        placeholder = {
            TextComponent(
                text = hint,
                fontFamily = inter,
                textSize = 15.sp,
                fontWeight = FontWeight(300),
                textColor = Color(0xff666666),
            )

        },
        maxLines = maxLines,
        minLines = minLines,
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction,
            autoCorrect = false,
            capitalization = KeyboardCapitalization.None
        ),
        shape = RoundedCornerShape(15.dp),
        keyboardActions = keyboardActions,
        leadingIcon = {
            if (leadingIcon != null){
                leadingIcon()
            }
        }
    )

}