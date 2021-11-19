package com.hamid.template.utils


import android.content.Context
import android.util.Patterns
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Matcher
import java.util.regex.Pattern

//validation util for validating app value
object ValidationUtil {

    fun showToast(context: Context, message: String) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun isNullOrEmpty(input: String?): Boolean = input == null || input.isEmpty()

    fun isValidUsername(username: String?, regex: String = "^(?=.*[a-z])(?=.*[0-9]).*\$"): Int {
        return when {
            isNullOrEmpty(username) -> 1
            username!!.length < 8 -> 2
            !Pattern.matches(regex, username) -> 3
            else -> 4
        }
    }

    fun isValidName(fname: String): Boolean {
        if (fname.isEmpty()) {
            return false
        } else if (!fname.matches("^(?! )[A-Z a-z]+$".toRegex())) {
            return false
        }
        return true
    }

    fun isValidNickName(username: String?): Int {
        return when {
            isNullOrEmpty(username) -> 1
            username?.trim()?.length!! < 2 -> 2
            else -> 3
        }
    }

    fun isValidEmail(email: String?): Int {
        return when {
            isNullOrEmpty(email) -> 1
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> 2
            else -> 3
        }
    }

    fun isValidEmailOrPhone(email: String?): Int {
        return if (isNullOrEmpty(email)) {
            1
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches() ||
            Patterns.PHONE.matcher(email).matches()
        ) {
            3
        } else {
            2
        }
    }

    fun isValidMobile(phone: String): Int {

        return when {
            isNullOrEmpty(phone) -> 1
            !Patterns.PHONE.matcher(phone).matches() -> 2
            else -> 3
        }

    }

    fun isValidSSN(str: String?): Boolean {
        val regex = "^(?!000|666)[0-8][0-9]{2}(?!00)[0-9]{2}(?!0000)[0-9]{4}\$"
        val p: Pattern = Pattern.compile(regex)
        if (str == null) {
            return false
        }
        val m: Matcher = p.matcher(str)
        return m.matches()
    }

    fun isValidPassword(password: String?,regex: String = "^(?=.*[a-z])(?=.*[0-9]).*\$"): Int {
        return when {
            isNullOrEmpty(password) -> 1
            password!!.length < 8 -> 2
            !Pattern.matches(regex, password) -> 3
            else -> 4
        }

    }

    fun isValidPasswordLogin(password: String?): Int {
        return when {
            isNullOrEmpty(password) -> 1
            else -> 2
        }

    }

    fun isEmpty(etText: TextInputEditText): Int {
        return if (etText.text.toString().trim().isEmpty()) {
            0
        } else {
            1
        }
    }

    fun isEmpty(etText: AutoCompleteTextView): Int {
        return if (etText.text.toString().trim().isEmpty()) {
            0
        } else {
            1
        }
    }

      /*fun isEmpty(cBox: MaterialCheckBox): Int {
        return if (cBox.isEnabled) {
            0
        } else {
            1
        }
    }*/

}
