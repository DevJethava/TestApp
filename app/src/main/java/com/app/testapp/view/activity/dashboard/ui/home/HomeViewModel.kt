package com.app.testapp.view.activity.dashboard.ui.home

import com.app.testapp.helper.Constants
import com.app.testapp.helper.Preference
import com.app.testapp.helper.async
import com.app.testapp.model.data.request.LoginRequest
import com.app.testapp.model.data.response.DashboardResponse
import com.app.testapp.model.data.response.LoginResponse
import com.app.testapp.model.repository.ApiRepository
import com.app.testapp.viewmodel.ParentViewModel
import io.reactivex.Single

class HomeViewModel(
    repository: ApiRepository,
    preference: Preference
) : ParentViewModel(repository, preference) {

    fun getDashboardData(): Single<DashboardResponse> {
        val accessToken: String = preference.user.data?.accessToken ?: ""
        return repository.getDashboardData(Constants.CLIENT_ID, accessToken).async(0)
            .doOnSuccess { stopLoad() }
            .doOnSubscribe {
                startLoad()
            }
            .doAfterTerminate {
                stopLoad()
            }
    }
}