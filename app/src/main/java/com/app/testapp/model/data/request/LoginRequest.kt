package com.app.testapp.model.data.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("password")
    @Expose
    val password: String,
    @SerializedName("grant_type")
    @Expose
    val grantType: String,
    @SerializedName("client_id")
    @Expose
    val clientId: String,
    @SerializedName("client_secret")
    @Expose
    val clientSecret: String,
    @SerializedName("scope")
    @Expose
    val scope: String,
    @SerializedName("fcm_token")
    @Expose
    val fcmToken: String
)