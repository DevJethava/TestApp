package com.app.testapp.model.data.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("message")
    @Expose
    val message: String? = null,
    @SerializedName("data")
    @Expose
    val data: Data? = null
)

data class Data(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("username")
    @Expose
    val username: String,
    @SerializedName("password")
    @Expose
    val password: String,
    @SerializedName("phone_number")
    @Expose
    val phoneNumber: String,
    @SerializedName("first_name")
    @Expose
    val firstName: String,
    @SerializedName("last_name")
    @Expose
    val lastName: String,
    @SerializedName("image")
    @Expose
    val image: String? = null,

    @SerializedName("birthday")
    @Expose
    val birthday: String? = null,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("image_url")
    @Expose
    val imageUrl: String,
    @SerializedName("token_type")
    @Expose
    val tokenType: String,
    @SerializedName("expires_in")
    @Expose
    val expiresIn: Int,
    @SerializedName("access_token")
    @Expose
    val accessToken: String,
    @SerializedName("refresh_token")
    @Expose
    val refreshToken: String
)