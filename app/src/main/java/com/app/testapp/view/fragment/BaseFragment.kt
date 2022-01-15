package com.app.testapp.view.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.app.testapp.view.activity.login.MainActivity
import com.app.testapp.viewmodel.ParentViewModel
import com.uber.autodispose.AutoDispose
import com.uber.autodispose.SingleSubscribeProxy
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import io.reactivex.Single

/**
 * BaseFragment
 * All Fragment parent class
 * contains Common methods for Fragments
 */
open class BaseFragment : Fragment() {
    var superViewModel: ParentViewModel? = null
    var strTag = ""

    fun setParentViewModel(viewModel: ParentViewModel) {
        this.superViewModel = viewModel

    }

    fun <T> Single<T>.bindLifeCycle(owner: LifecycleOwner): SingleSubscribeProxy<T> =
        this.`as`(
            AutoDispose.autoDisposable(
                AndroidLifecycleScopeProvider.from(
                    owner,
                    Lifecycle.Event.ON_DESTROY
                )
            )
        )

    fun onBackPress() {
        requireActivity().onBackPressed()
    }

    fun goToLogin() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}