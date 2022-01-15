package com.app.testapp.view.activity.login

import com.app.testapp.helper.Preference
import com.app.testapp.helper.async
import com.app.testapp.model.data.request.LoginRequest
import com.app.testapp.model.data.response.LoginResponse
import com.app.testapp.model.repository.ApiRepository
import com.app.testapp.viewmodel.ParentViewModel
import io.reactivex.Single

class LoginViewModel(
    repository: ApiRepository,
    preference: Preference
) : ParentViewModel(repository, preference) {
    fun userLogin(loginRequest: LoginRequest): Single<LoginResponse> {
        return repository.login(loginRequest).async(0)
            .doOnSuccess { stopLoad() }
            .doOnSubscribe {
                startLoad()
            }
            .doAfterTerminate {
                stopLoad()
            }
    }
}