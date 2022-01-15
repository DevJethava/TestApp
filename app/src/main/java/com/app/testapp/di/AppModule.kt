package com.app.testapp.di

import com.app.testapp.BuildConfig
import com.app.testapp.helper.Preference
import com.app.testapp.helper.ResponseInterceptor
import com.app.testapp.model.remote.RestApiService
import com.app.testapp.model.repository.ApiRepository
import com.app.testapp.model.repository.ApiRepositoryImpl
import com.app.testapp.view.activity.dashboard.ui.home.HomeViewModel
import com.app.testapp.view.activity.dashboard.ui.profile.ProfileViewModel
import com.app.testapp.view.activity.login.LoginViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiModule = module {

    fun provideRestApi(retrofit: Retrofit): RestApiService {
        return retrofit.create(RestApiService::class.java)
    }
    single { provideRestApi(get()) }
    single { Preference(get()) }
}

val repositoryModule = module {

    fun provideAPIRepository(api: RestApiService): ApiRepository {
        return ApiRepositoryImpl(api)
    }
    single { provideAPIRepository(get()) }
}

val viewModelModule = module {
    viewModel {
        LoginViewModel(
            repository = get(),
            preference = get()
        )
    }

    viewModel {
        HomeViewModel(
            repository = get(),
            preference = get()
        )
    }

    viewModel {
        ProfileViewModel(
            repository = get(),
            preference = get()
        )
    }
}

val networkModule = module {
    val connectTimeout: Long = 180// 20s
    val readTimeout: Long = 180 // 20s

    /*
    Http logging client
     */
    fun provideHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
            .connectTimeout(connectTimeout, TimeUnit.SECONDS)
            .readTimeout(readTimeout, TimeUnit.SECONDS)
        if (true) {
            val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        }
        okHttpClientBuilder.addInterceptor(ResponseInterceptor())
        okHttpClientBuilder.build()
        return okHttpClientBuilder.build()
    }

    /*
    Retrofit for API
     */
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    single { provideHttpClient() }
    single {
        provideRetrofit(get())
    }
}

val appModule = listOf(apiModule, repositoryModule, viewModelModule, networkModule)