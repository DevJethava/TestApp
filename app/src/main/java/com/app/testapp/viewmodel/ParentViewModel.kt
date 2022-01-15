package com.app.testapp.viewmodel


import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.testapp.helper.Preference
import com.app.testapp.model.repository.ApiRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * ParentViewModel
 * All ViewModel parent class
 * contains Common methods for ViewModels
 */
open class ParentViewModel(
    val repository: ApiRepository,
    val preference: Preference
) : ViewModel() {

    val throwError = MutableLiveData<Throwable>()
    val exception: LiveData<Throwable> = throwError

    val compositeDisposable = CompositeDisposable()

    val loading = ObservableBoolean(false)

    fun startLoad() = loading.set(true)
    fun stopLoad() = loading.set(false)

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) compositeDisposable.dispose()
    }
}