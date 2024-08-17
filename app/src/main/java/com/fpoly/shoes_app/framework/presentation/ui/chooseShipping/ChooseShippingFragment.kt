package com.fpoly.shoes_app.framework.presentation.ui.chooseShipping

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fpoly.shoes_app.R

class ChooseShippingFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseShippingFragment()
    }

    private val viewModel: ChooseShippingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_choose_shipping, container, false)
    }
}