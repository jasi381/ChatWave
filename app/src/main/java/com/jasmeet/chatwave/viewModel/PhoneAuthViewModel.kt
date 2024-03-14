package com.jasmeet.chatwave.viewModel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.jasmeet.chatwave.screens.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class PhoneAuthViewModel @Inject constructor(private val auth : FirebaseAuth) :ViewModel() {

    private val message = mutableStateOf("")
    val errorMessage = mutableStateOf("")
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null


    fun sendVerificationCode(
        activity : Activity,
        onVerificationIdReceived:(String)->Unit,
        phoneNumber:String
    ){

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object:PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    errorMessage.value = p0.message.toString()
                    Log.d("Vm",p0.message.toString())
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    onVerificationIdReceived(verificationId)
                    resendToken = token
                    Log.d("Toke", token.toString())
                }
            }).build()

        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    fun signInWithPhoneAuthCredential(
        activity: Activity,
        verificationId: String?,
        otp:String
    ){
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
            verificationId!!, otp
        )
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    message.value = "Verification successful.."
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        errorMessage.value = "Invalid OTP"
                    }
                    if (task.exception is FirebaseException){
                        errorMessage.value = task.exception?.message.toString()
                    }
                }
            }
    }

//    fun resendVerificationCode(phoneNumber: String,activity: Activity) {
//        val token = resendToken ?: return // Return early if resendToken is null
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                override fun onVerificationCompleted(p0: PhoneAuthCredential) {}
//
//                override fun onVerificationFailed(p0: FirebaseException) {
//                    Log.d("Vm", p0.message.toString())
//                }
//
//                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
//                    resendToken = token
//                    Log.d("Success", "Success")
//                }
//            })
//            .setForceResendingToken(token)
//            .build()
//
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }

}