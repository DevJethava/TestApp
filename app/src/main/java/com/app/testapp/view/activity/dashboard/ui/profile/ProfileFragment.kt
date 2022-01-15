package com.app.testapp.view.activity.dashboard.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.app.testapp.R
import com.app.testapp.databinding.FragmentProfileBinding
import com.app.testapp.helper.Utils
import com.app.testapp.model.data.response.LoginResponse
import com.app.testapp.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProfileFragment : BaseFragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel by viewModel<ProfileViewModel>()

    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_profile,
                container,
                false
            )
        init()
        return binding.root
    }

    private fun init() {
        val isLogin = viewModel.preference.isLogin
        if (isLogin) {
            val user: LoginResponse = viewModel.preference.user
            if (user != null) {

                val date: Date = Calendar.getInstance().time
                binding.tvDate.text = Utils.convertDate(date)

                binding.tvuserName.text = "UserName: " + user.data?.username
                binding.tvEmail.text = "Email: " + user.data?.email
                binding.tvPhoneNumber.text = "PhoneNumber: " + user.data?.phoneNumber

            }
        } else {
            viewModel.preference.isLogin = false
            goToLogin()
        }

        binding.btnLogout.setOnClickListener {
            viewModel.preference.clearPreference()
            viewModel.preference.isLogin = false
            goToLogin()
        }

        binding.ivImageView.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            binding.ivImageView.setImageURI(imageUri)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}