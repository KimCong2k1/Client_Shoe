package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import com.fpoly.shoes_app.databinding.FragmentShoeDetailBinding
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoeDetailFragment : BaseFragment<FragmentShoeDetailBinding, ShoeDetailViewModel>(
    FragmentShoeDetailBinding::inflate,
    ShoeDetailViewModel::class.java
) {
    override fun setupViews() {

    }

    override fun bindViewModel() {

    }

    override fun setOnClick() {

    }

}