package com.fpoly.shoes_app.framework.presentation.ui.cart

import androidx.core.view.isVisible
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentCartBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    FragmentCartBinding::inflate,
    CartViewModel::class.java
) {
    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)
        binding.run {
            headerLayout.imgBack.isVisible = false
            headerLayout.tvTitle.text = getString(R.string.cart_title)
        }
    }

    override fun bindViewModel() {

    }

    override fun setOnClick() {
    }
}