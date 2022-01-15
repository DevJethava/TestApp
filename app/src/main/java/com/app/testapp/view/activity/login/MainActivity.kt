package com.app.testapp.view.activity.login

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.app.testapp.R
import com.app.testapp.databinding.ActivityMainBinding
import com.app.testapp.helper.Constants
import com.app.testapp.helper.Utils
import com.app.testapp.helper.toast
import com.app.testapp.model.data.request.LoginRequest
import com.app.testapp.view.activity.BaseActivity
import com.app.testapp.view.activity.dashboard.DashboardActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.activity = this
        binding.vm = viewModel
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val isLogin = viewModel.preference.isLogin
        if (isLogin) {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {

                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                    p1: PermissionToken?
                ) {

                }
            }).check()
    }


    fun onbtnLoginClick() {
        if (binding.etUsername.text.toString().isEmpty()) {
            toast("Username is Empty")
            return
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            toast("Password is Empty")
            return
        }

        loginData(binding.etUsername.text.toString(), binding.etPassword.text.toString())

    }

    private fun loginData(username: String, password: String) {
        val dialog: AlertDialog = Utils.setupLoadingDialog(this)
        if (Utils.isConnectionAvailable(this)) {
            dialog.show()
            val loginRequest = LoginRequest(
                username,
                Utils.md5Hash(password),
                "password",
                Constants.CLIENT_ID,
                Constants.CLIENT_SECRET,
                "",
                ""
            )
            viewModel.userLogin(loginRequest)
                .bindLifeCycle(this).subscribe(
                    {
                        dialog.dismiss()
                        if (it.status == "success") {
                            viewModel.preference.isLogin = true
                            viewModel.preference.user = it
                            startActivity(Intent(this, DashboardActivity::class.java))
                        } else {
                            toast(it.message ?: "")
                        }
                    },
                    {
                        dialog.dismiss()
                        Log.e(TAG!!, it.message!!)
                    }
                )
            dialog.dismiss()
        } else {
            dialog.dismiss()
            this.toast(getString(R.string.internet_not_available))
        }
    }
}