package com.fpoly.shoes_app.framework.presentation.ui.history

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavOptions
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentHistoryBinding
import com.fpoly.shoes_app.databinding.FragmentLoginScreenBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.login.LoginViewModel

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryViewModel>(
    FragmentHistoryBinding::inflate, HistoryViewModel::class.java
) {

    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)

    }

    override fun bindViewModel() {
    }

    override fun setOnClick() {
        binding.allHistory.setOnClickListener {
            fragmentManager?.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            navController!!.navigate(
                R.id.detailHistoryFragment, null, NavOptions.Builder().setPopUpTo(
                    navController!!.currentDestination?.id ?: -1, true
                ).build()
            )
        }
    }
}