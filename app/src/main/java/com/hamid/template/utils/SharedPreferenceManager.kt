package com.hamid.template.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.hamid.template.ui.loginAndRegister.logResponseModel.LogInResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManager @Inject constructor(@ApplicationContext context: Context) {
    private val NAME = "demo"
    private val MODE = Context.MODE_PRIVATE
    private val IS_FIRST_RUN_PREF = Pair("is_first_run", false)
    private val userLogInResponse=Pair("userLogInResponse","")
    private val last_name=Pair("last_name","")
    private val user_email=Pair("user_email","")
    private val phone_number=Pair("phone_number","")
    private val country=Pair("country","")
    private val city=Pair("city","")
    private val join_date=Pair("join_date","")
    private val linked_in=Pair("linked_in","")
    private val bio=Pair("bio","")
    private val webLangauge=Pair("weblanguage","en")
    private val token=Pair("token","String")
    private var tripType=Pair("TripType",Constants.Up)



    val prefs = context.getSharedPreferences(NAME, MODE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var getTripType: String
        get() = prefs.getString(tripType.first, tripType.second).HandleNullToken(tripType.second)
        set(value) = prefs.edit {
            it.putString(tripType.first, value)
        }

    var getToken: String
        get() = prefs.getString(token.first, token.second).HandleNullToken()
        set(value) = prefs.edit {
            it.putString(token.first, value)
        }

    var firstRun: Boolean
        get() = prefs.getBoolean(IS_FIRST_RUN_PREF.first, IS_FIRST_RUN_PREF.second)
        set(value) = prefs.edit {
            it.putBoolean(IS_FIRST_RUN_PREF.first, value)
        }
    var UserLogInResponse: LogInResponse?
        get() = prefs.getString(userLogInResponse.first, userLogInResponse.second).userLogIN()
        set(value) = prefs.edit { it.putString(userLogInResponse.first, Gson().toJson(value))
        }
    var getLastName: String?
        get() = prefs.getString(last_name.first, last_name.second)
        set(value) = prefs.edit {
            it.putString(last_name.first, value)
        }
    var getEmail: String?
        get() = prefs.getString(user_email.first, user_email.second)
        set(value) = prefs.edit {
            it.putString(user_email.first, value)
        }
    var getPhoneNumber: String?
        get() = prefs.getString(phone_number.first, phone_number.second)
        set(value) = prefs.edit {
            it.putString(phone_number.first, value)
        }
    var getCountry: String?
        get() = prefs.getString(country.first, country.second)
        set(value) = prefs.edit {
            it.putString(country.first, value)
        }
    var getCity: String?
        get() = prefs.getString(city.first, city.second)
        set(value) = prefs.edit {
            it.putString(city.first, value)
        }
    var getJoiningDate: String?
        get() = prefs.getString(join_date.first, join_date.second)
        set(value) = prefs.edit {
            it.putString(join_date.first, value)
        }
    var getLinkedIn: String?
        get() = prefs.getString(linked_in.first, linked_in.second)
        set(value) = prefs.edit {
            it.putString(linked_in.first, value)
        }
    var getBio: String?
        get() = prefs.getString(bio.first, bio.second)
        set(value) = prefs.edit {
            it.putString(bio.first, value)
        }
    var getWeblanguage: String?
        get() = prefs.getString(webLangauge.first, webLangauge.second)
        set(value) = prefs.edit {
            it.putString(webLangauge.first, value)
        }
    fun deleteAll() {
        prefs.edit().clear().apply()
    }
}