package com.jasmeet.chatwave.screens.login

import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.jasmeet.chatwave.R
import com.jasmeet.chatwave.appComponent.Loader
import com.jasmeet.chatwave.appComponent.OtpInputField
import com.jasmeet.chatwave.appComponent.TextComponent
import com.jasmeet.chatwave.receiver.OTPReceiver
import com.jasmeet.chatwave.receiver.startSMSRetrieverClient
import com.jasmeet.chatwave.screens.Screens
import com.jasmeet.chatwave.viewModel.PhoneAuthViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpVerificationScreen(
    verificationId: String?,
    navHost: NavHostController,
    phoneNumber: String?
) {


    val screenOrientation = LocalConfiguration.current.orientation
    val context = LocalContext.current
    var otpValue by remember { mutableStateOf("") }
    var isOtpFilled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()

    OtpReceiverEffect(
        context = context,
        onOtpReceived = { otp ->
            otpValue = otp
            if (otpValue.length == 6) {
                keyboardController?.hide()
                isOtpFilled = true
            }
        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .imePadding()
            .then(
                if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) Modifier
                    .padding(horizontal = 20.dp)
                    .navigationBarsPadding()
                else Modifier.padding(horizontal = 22.dp)
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .then(
                    if (screenOrientation == Configuration.ORIENTATION_LANDSCAPE) Modifier.padding(
                        top = 10.dp
                    ) else Modifier.padding(top = 18.dp)
                )
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            TextComponent(
                text = "OTP Verification",
                modifier = Modifier.align(Alignment.Center),
                textSize = 20.sp,
                fontWeight = FontWeight.W600
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        Loader(
            rawRes = R.raw.verify_otp,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.3f)
        )
        Spacer(modifier = Modifier.height(25.dp))

        TextComponent(
            text = "Enter OTP",
            textSize = 22.sp,
            fontWeight = FontWeight.W700
        )
        TextComponent(
            text = "An OTP has been sent to\n$phoneNumber",
            fontWeight = FontWeight.Normal,
            textColor = Color(0xff595959),
            textSize = 15.sp,
            modifier = Modifier.padding(top = 6.dp),
            lineHeight = 18.sp
        )

        OtpInputField(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 25.dp)
                .focusRequester(focusRequester),
            otpText = otpValue,
            shouldCursorBlink = false,
            onOtpModified = { value, otpFilled ->
                otpValue = value
                isOtpFilled = otpFilled
                if (otpFilled) {
                    phoneAuthViewModel.signInWithPhoneAuthCredential(
                        activity = context as Activity,
                        verificationId = verificationId,
                        otp = otpValue
                    )
                    keyboardController?.hide()
                    navHost.navigate(Screens.SuccessScreen.route)
                }
            }
        )


    }

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpReceiverEffect(
    context: Context,
    onOtpReceived: (String) -> Unit
) {
    val otpReceiver = remember { OTPReceiver() }

    /**
     * This function should not be used to listen for Lifecycle.Event.ON_DESTROY because Compose
     * stops recomposing after receiving a Lifecycle.Event.ON_STOP and will never be aware of an
     * ON_DESTROY to launch onEvent.
     *
     * This function should also not be used to launch tasks in response to callback events by way
     * of storing callback data as a Lifecycle.State in a MutableState. Instead, see currentStateAsState
     * to obtain a State that may be used to launch jobs in response to state changes.
     */
    LifecycleResumeEffect {
        // add ON_RESUME effect here
        Log.e("OTPReceiverEffect", "SMS retrieval has been started.")
        startSMSRetrieverClient(context)
        otpReceiver.init(object : OTPReceiver.OTPReceiveListener {
            override fun onOTPReceived(otp: String?) {
                Log.e("OTPReceiverEffect ", "OTP Received: $otp")
                otp?.let { onOtpReceived(it) }
                try {
                    Log.e("OTPReceiverEffect ", "Unregistering receiver")
                    context.unregisterReceiver(otpReceiver)
                } catch (e: IllegalArgumentException) {
                    Log.e("OTPReceiverEffect ", "Error in registering receiver: ${e.message}}")
                }
            }

            override fun onOTPTimeOut() {
                Log.e("OTPReceiverEffect ", "Timeout")
            }
        })
        try {
            Log.e("OTPReceiverEffect ", "Lifecycle.Event.ON_RESUME")
            Log.e("OTPReceiverEffect ", "Registering receiver")
            context.registerReceiver(
                otpReceiver,
                IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION),
                Context.RECEIVER_EXPORTED
            )
        } catch (e: IllegalArgumentException) {
            Log.e("OTPReceiverEffect ", "Error in registering receiver: ${e.message}}")
        }
        onPauseOrDispose {
            // add clean up for work kicked off in the ON_RESUME effect here
            try {
                Log.e("OTPReceiverEffect ", "Lifecycle.Event.ON_PAUSE")
                Log.e("OTPReceiverEffect ", "Unregistering receiver")
                context.unregisterReceiver(otpReceiver)
            } catch (e: IllegalArgumentException) {
                Log.e("OTPReceiverEffect ", "Error in unregistering receiver: ${e.message}}")
            }
        }
    }
}

