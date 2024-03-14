package com.jasmeet.chatwave.screens.login

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun EmailLoginUi(navHost: NavHostController) {
//
    LazyColumn(Modifier.fillMaxSize()) {
        items(100){
            Text(text = it.toString(), modifier = Modifier.padding(8.dp))
        }
    }


}