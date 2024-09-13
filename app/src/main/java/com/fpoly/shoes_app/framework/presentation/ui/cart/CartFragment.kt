package com.fpoly.shoes_app.framework.presentation.ui.cart

import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentCartBinding
import com.fpoly.shoes_app.framework.domain.model.CheckoutArgs
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.utility.formatPriceShoe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding, CartViewModel>(
    FragmentCartBinding::inflate,
    CartViewModel::class.java
) {

    @Inject
    lateinit var cartAdapter: CartAdapter

    override fun setupViews() {
        (requireActivity() as? MainActivity)?.showBottomNavigation(true)
        binding.run {
            headerLayout.imgBack.isVisible = false
            headerLayout.tvTitle.text = getString(R.string.cart_title)
            rcvCart.adapter = cartAdapter
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                cartAdapter.submitList(state.shoes)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.isEnableButton }
                .collect { enable ->
                    binding.tvButton.isEnabled = enable
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.total }
                .collect { total ->
                    binding.tvPriceTotal.text = total.formatPriceShoe()
                }
        }

        lifecycleScope.launch {
            viewModel.uiState.mapNotNull {
                it.isLoading
            }.distinctUntilChanged().collect {
                showProgressbar(it)
            }
        }
    }

    override fun setOnClick() {
        cartAdapter.setOnClick {
            navController?.navigate(
                CartFragmentDirections.actionCartFragmentToShoeDetailFragment(it)
            )
        }

        cartAdapter.setOnClickRemove {
            showAlertDialog(
                title = getString(R.string.cart_delete_title),
                button = getString(R.string.agree),
                buttonCancel = getString(R.string.cancel),
                onClick = { viewModel.removeShoeCart(it) }
            )
        }

        //TODO update Cart
        cartAdapter.setOnClickPlus {
            viewModel.handlePlus(it)
        }

        //TODO update Cart
        cartAdapter.setOnClickReduce {
            viewModel.handleReduce(it)
        }

        binding.tvButton.setOnClickListener {
            navController?.navigate(
                CartFragmentDirections.actionCartFragmentToCheckoutFragment(
                    CheckoutArgs(
                        carts = viewModel.uiState.value.carts,
                        totalCart = viewModel.uiState.value.total ?: 0,
                    )
                )
            )
        }
    }
}