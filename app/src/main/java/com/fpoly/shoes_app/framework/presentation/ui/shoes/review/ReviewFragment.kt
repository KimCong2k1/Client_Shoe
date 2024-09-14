package com.fpoly.shoes_app.framework.presentation.ui.shoes.review

import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentReviewBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment

class ReviewFragment : BaseFragment<FragmentReviewBinding, ReviewViewModel>(
    FragmentReviewBinding::inflate,
    ReviewViewModel::class.java
) {
    override fun setupViews() {
        (requireActivity() as? MainActivity)?.showBottomNavigation(true)
        binding.run {
            headerLayout.tvTitle.text = getString(R.string.review)
        }
    }

    override fun bindViewModel() {

    }

    override fun setOnClick() {
        binding.headerLayout.imgBack.setOnClickListener {
            navController?.popBackStack()
        }
    }
}