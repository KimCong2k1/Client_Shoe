package com.fpoly.shoes_app.framework.presentation.ui.history.seeAllHistory

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentHomeBinding
import com.fpoly.shoes_app.databinding.FragmentSeeAllHistoryBinding
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.home.HomeViewModel

class SeeAllHistoryFragment : BaseFragment<FragmentSeeAllHistoryBinding, SeeAllHistoryViewModel>(
    FragmentSeeAllHistoryBinding::inflate,
    SeeAllHistoryViewModel::class.java
) {
    override fun setupViews() {

    }

    override fun bindViewModel() {
    }

    override fun setOnClick() {
        binding.toolbar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}