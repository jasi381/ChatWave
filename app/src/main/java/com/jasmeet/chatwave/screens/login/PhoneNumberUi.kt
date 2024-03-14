package com.jasmeet.chatwave.screens.login

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.jasmeet.chatwave.appComponent.ButtonComponent
import com.jasmeet.chatwave.appComponent.TextComponent
import com.jasmeet.chatwave.appComponent.TextFieldComponent
import com.jasmeet.chatwave.appComponent.customClickable
import com.jasmeet.chatwave.data.Country
import com.jasmeet.chatwave.data.countryList
import com.jasmeet.chatwave.screens.Screens
import com.jasmeet.chatwave.viewModel.PhoneAuthViewModel
import java.net.URLEncoder

@Composable
fun PhoneNumberUi(
    navHost: NavHostController,
    modifier: Modifier = Modifier,
    onCodeSelected: (Country) -> Unit
) {

    val number = rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val selectedItem = remember {
        mutableStateOf(Country(code = "", name = "", countryCode = ""))
    }
    val interactionSource = remember { MutableInteractionSource() }

    val searchCountry = rememberSaveable {
        mutableStateOf("")
    }
    val isSearchClicked = rememberSaveable {
        mutableStateOf(false)
    }

    val phoneAuthViewModel :PhoneAuthViewModel = hiltViewModel()
    val context = LocalContext.current


    Column(
        modifier = modifier
    ) {
        TextFieldComponent(
            modifier = Modifier
                .padding(top = 30.dp)
                .fillMaxWidth(),
            value = number.value,
            onValueChange = {
                number.value = it
            },
            hint = "Phone Number",
            leadingIcon = {
                Surface(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .customClickable {
                            expanded = !expanded
                        },
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (selectedItem.value.flag != null) {
                            Image(
                                painter = painterResource(id = selectedItem.value.flag!!),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 5.dp)
                                    .size(30.dp)
                            )
                        }
                        TextComponent(
                            text = if (selectedItem.value.name.isEmpty()) "Country"
                            else selectedItem.value.code
                        )
                        Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                    }

                }

            },
            keyboardActions = KeyboardActions(

                onDone = {
                    signInWithMobileNumber(number, selectedItem, phoneAuthViewModel, context, navHost)
                }
            ),
            imeAction = ImeAction.Done
        )

        if (expanded) {
            Dialog(
                onDismissRequest = {
                    expanded = !expanded
                    selectedItem.value = Country(code = "", name = "", countryCode = "")
                    onCodeSelected.invoke(Country(code = "", name = "", countryCode = ""))
                    isSearchClicked.value = false
                }
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.White, MaterialTheme.shapes.large)
                        .width(LocalConfiguration.current.screenWidthDp.dp * 0.75f)
                        .height(LocalConfiguration.current.screenHeightDp.dp * 0.5f),
                    contentAlignment = Alignment.Center
                ) {
                    LazyColumn(
                        Modifier.fillMaxSize()
                    ) {
                        if (!isSearchClicked.value) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    TextComponent(
                                        text = "Select your Country",
                                        modifier = Modifier.padding(start = 12.dp),
                                        textSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    IconButton(
                                        onClick = {
                                            isSearchClicked.value = !isSearchClicked.value
                                        },
                                    ) {
                                        Icon(
                                            imageVector = Icons.TwoTone.Search,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                        if (isSearchClicked.value) {
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    TextFieldComponent(
                                        value = searchCountry.value,
                                        onValueChange = {
                                            searchCountry.value = it
                                        },
                                        hint = "Search",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .weight(1f),
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.TwoTone.Search,
                                                contentDescription = "search"
                                            )
                                        },
                                        keyboardType = KeyboardType.Text
                                    )

                                    TextComponent(
                                        text = "Cancel",
                                        textSize = 14.sp,
                                        modifier = Modifier
                                            .padding(start = 5.dp)
                                            .customClickable {
                                                isSearchClicked.value = !isSearchClicked.value
                                                searchCountry.value = ""

                                            }
                                            .padding(2.dp),
                                        textColor = Color.Red,

                                        )

                                }

                            }
                        }

                        val filteredCountries = countryList.filter {
                            it.name.contains(searchCountry.value, ignoreCase = true) ||
                                    it.code.contains(searchCountry.value, ignoreCase = true)
                        }
                        items(if (isSearchClicked.value) filteredCountries else countryList) { country ->
                            Row(
                                Modifier
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null

                                    ) {
                                        selectedItem.value =
                                            Country(
                                                country.flag,
                                                country.name,
                                                country.code,
                                                countryCode = country.countryCode
                                            )
                                        expanded = !expanded
                                        isSearchClicked.value = !isSearchClicked.value
                                        searchCountry.value = ""
                                        onCodeSelected.invoke(country)
                                    }
                                    .padding(8.dp)
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row {
                                    country.flag?.let { painterResource(id = it) }?.let {
                                        Image(
                                            painter = it,
                                            contentDescription = country.name,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    TextComponent(
                                        text = country.name,
                                        modifier = Modifier.padding(horizontal = 5.dp)
                                    )
                                }
                                TextComponent(text = country.code)

                            }
                            if (countryList.last() != country)
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
                        }
                    }
                }

            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        ButtonComponent(
            shape = RoundedCornerShape(15.dp),
            text = "Request OTP",
            onClick = {
                signInWithMobileNumber(number, selectedItem, phoneAuthViewModel, context, navHost)
            },
            modifier = Modifier.fillMaxWidth(),
            textModifier = Modifier.padding(6.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp,
            ),
            enabled = !(number.value.isEmpty() || selectedItem.value.name.isEmpty()),
            textColor = if (number.value.isEmpty() || selectedItem.value.name.isEmpty()) Color.Gray else Color.White,
            fontWeight = FontWeight.W600

        )
    }
}


private fun signInWithMobileNumber(
    number: MutableState<String>,
    selectedItem: MutableState<Country>,
    phoneAuthViewModel: PhoneAuthViewModel,
    context: Context,
    navHost: NavHostController
) {
    if (!(number.value.isEmpty() || selectedItem.value.name.isEmpty())) {
        val combinedNumber = selectedItem.value.code + number.value
        phoneAuthViewModel.sendVerificationCode(
            activity = context as Activity,
            phoneNumber = combinedNumber,
            onVerificationIdReceived = { verificationId ->

                navHost.navigate(
                    Screens.OtpScreen.passVerificationIdPhoneNumber(phoneNumber = selectedItem.value.code + number.value, verificationId = verificationId),
                )

            }
        )
    } else {
        Log.d("Error", "PhoneNumberUi: Error")
    }
}

@Preview
@Composable
private fun PhoneNumberUiPreview() {
    val navHost = NavHostController(LocalContext.current)
    PhoneNumberUi(navHost = navHost, onCodeSelected = {})
}


