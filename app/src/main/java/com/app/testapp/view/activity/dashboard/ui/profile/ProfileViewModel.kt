package com.app.testapp.view.activity.dashboard.ui.profile

import com.app.testapp.helper.Preference
import com.app.testapp.model.repository.ApiRepository
import com.app.testapp.viewmodel.ParentViewModel

class ProfileViewModel(
    repository: ApiRepository,
    preference: Preference
) : ParentViewModel(repository, preference) {
}