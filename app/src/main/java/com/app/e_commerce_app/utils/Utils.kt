package com.app.e_commerce_app.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.util.*

object Utils {
    fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    fun formatNumber(number : Long) : String {
        val formatter = NumberFormat.getNumberInstance(Locale.getDefault())
        formatter.maximumFractionDigits = 0
        val result = formatter.format(number)
//        return result.replace(",", ".").plus(" VND")
        return number.toString()
    }
    fun hideSoftKeyboard(view: View, context: Context) {
        val imm: InputMethodManager? =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
    }
    fun validatePassword(password: String): Boolean {
        if (password.length < 8) {
            return false
        }

        var hasUpperCase = false;
        var hasDigitCharacter = false;
        var hasLowerCase = false;

        var count = 0
        for (i in password.indices) {
            val character = password[i]
            when {
                Character.isDigit(character) -> {
                    hasDigitCharacter = true;
                    count++;
                }
                Character.isUpperCase(character) -> {
                    hasUpperCase = true;
                    count++;
                }
                Character.isLowerCase(character) -> {
                    hasLowerCase = true;
                    count++;
                }
            }
        }

        val hasSpecialCharacter = count != password.length

        if (!hasUpperCase || !hasDigitCharacter || !hasLowerCase || !hasSpecialCharacter) {
            return false
        }

        return true
    }

}