package com.app.testapp.model.repository

import com.app.testapp.model.data.request.LoginRequest
import com.app.testapp.model.data.response.DashboardResponse
import com.app.testapp.model.data.response.LoginResponse
import com.app.testapp.model.remote.RestApiService
import io.reactivex.Single

interface ApiRepository {

    fun login(
        loginRequest: LoginRequest,
    ): Single<LoginResponse>

    fun getDashboardData(
        clientId: String,
        accessToken: String
    ): Single<DashboardResponse>
}

class ApiRepositoryImpl constructor(
    private val remote: RestApiService,
) : ApiRepository {

    override fun login(
        loginRequest: LoginRequest,
    ): Single<LoginResponse> = remote.login(loginRequest = loginRequest)

    override fun getDashboardData(
        clientId: String,
        accessToken: String
    ): Single<DashboardResponse> = remote.getDashboardData(clientId, accessToken)
}

