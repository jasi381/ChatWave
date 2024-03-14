package com.jasmeet.chatwave.screens

/**
 * Created by Jasmeet Singh
 * on 13 March 2024.
 */

const val VERIFICATION_ID = "verificationId"
const val PHONE_NUMBER = "phoneNumber"

sealed class Screens(val route :String) {
    data object LoginScreen : Screens("login_screen")
    data object SuccessScreen : Screens("success_screen")

    data object OtpScreen: Screens("otp_screen/{$PHONE_NUMBER}/{$VERIFICATION_ID}"){

        fun passVerificationIdPhoneNumber(
            verificationId:String,
            phoneNumber:String
        ):String{
            return this.route
                .replace("{$VERIFICATION_ID}",verificationId)
                .replace("{$PHONE_NUMBER}",phoneNumber)
        }
    }
}