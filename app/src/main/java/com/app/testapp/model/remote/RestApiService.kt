package com.app.testapp.model.remote

import com.app.testapp.model.data.request.LoginRequest
import com.app.testapp.model.data.response.DashboardResponse
import com.app.testapp.model.data.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestApiService {
    // Login
    @POST("laundry/login")
    fun login(
        @Body loginRequest: LoginRequest,
    ): Single<LoginResponse>

    // Get Dashboard
    @GET("laundry/dashboard")
    fun getDashboardData(
        @Header("clientid") clientId: String,
        @Header("accesstoken") accessToken: String
    ): Single<DashboardResponse>
}