package com.app.testapp.helper

import android.content.Context
import com.app.testapp.model.data.response.LoginResponse
import com.google.gson.Gson

class Preference(context: Context) {
    private val prefFileName = "com.app.testapp"
    private val prfIsLogin = "IsLogin"
    private val preUser = "User"

    private val preference = context.getSharedPreferences(prefFileName, 0)

    private val gson: Gson = Gson()

    fun clearPreference() {
        preference.edit().clear().apply()
    }

    var isLogin: Boolean
        get() = preference.getBoolean(prfIsLogin, false)
        set(value) = preference.edit().putBoolean(prfIsLogin, value).apply()

    var user: LoginResponse
        get() = gson.fromJson(
            preference.getString(this.preUser, "").toString(),
            LoginResponse::class.java
        )
        set(value) = preference.edit().putString(this.preUser, gson.toJson(value)).apply()
}