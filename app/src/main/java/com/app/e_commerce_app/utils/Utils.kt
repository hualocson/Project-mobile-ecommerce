package com.app.e_commerce_app.utils

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
        return result.replace(",", ".").plus(" VND")
    }
}