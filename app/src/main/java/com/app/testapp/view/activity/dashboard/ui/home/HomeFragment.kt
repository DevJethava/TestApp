package com.app.testapp.view.activity.dashboard.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.app.testapp.R
import com.app.testapp.databinding.FragmentHomeBinding
import com.app.testapp.helper.Utils
import com.app.testapp.helper.bindLifeCycle
import com.app.testapp.helper.toast
import com.app.testapp.view.fragment.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment() {
    private val TAG = HomeFragment::class.simpleName
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false
            )
        init()
        return binding.root
    }

    private fun init() {
        val isLogin = viewModel.preference.isLogin
        if (isLogin) {
            val dialog: AlertDialog = Utils.setupLoadingDialog(requireActivity())
            if (Utils.isConnectionAvailable(requireContext())) {
                dialog.show()
                viewModel.getDashboardData()
                    .bindLifeCycle(this).subscribe(
                        {
                            dialog.dismiss()
                            if (it.status == "success") {

                                binding.tvPickupOrdersTotal.text =
                                    (it.data?.pickupOrders?.total ?: "0").toString()
                                binding.tvPickupOrdersCompleted.text =
                                    (it.data?.pickupOrders?.completed ?: "0").toString()

                                binding.tvDeliveryOrdersCompleted.text =
                                    (it.data?.deliveryOrders?.completed ?: "0").toString()
                                binding.tvDeliveryOrdersTotal.text =
                                    (it.data?.deliveryOrders?.total ?: "0").toString()

                                binding.tvUnassignedOrdersTotal.text =
                                    (it.data?.unassignedOrders?.total ?: "0").toString()
                                binding.tvToBeProcessed.text =
                                    (it.data?.toBeProcessed?.total ?: "0").toString()

                            } else {
                                requireContext().toast(it.message ?: "")
                                viewModel.preference.isLogin = false
                                goToLogin()
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
                requireContext().toast(getString(R.string.internet_not_available))
            }
        } else {
            viewModel.preference.isLogin = false
            goToLogin()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}