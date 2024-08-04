package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fpoly.shoes_app.databinding.FragmentShoeDetailBinding
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.utility.formatPriceShoe
import com.fpoly.shoes_app.utility.formatSoldShoe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class ShoeDetailFragment : BaseFragment<FragmentShoeDetailBinding, ShoeDetailViewModel>(
    FragmentShoeDetailBinding::inflate,
    ShoeDetailViewModel::class.java
) {
    @Inject
    lateinit var imageShoeDetailAdapter: ImageShoeDetailAdapter

    private val args: ShoeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initShoeDetail(args.idShoe)
    }

    override fun setupViews() {
        binding.run {
            viewPagerImageShoe.adapter = imageShoeDetailAdapter
            binding.dotsIndicator.attachTo(viewPagerImageShoe)
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.mapNotNull {
                it.isLoading
            }.collect { isLoading ->
                showProgressbar(isLoading)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                imageShoeDetailAdapter.submitList(state.shoeDetail?.imagesShoe)
                binding.run {
                    tvNameShoe.text = state.shoeDetail?.name
                    tvSoldShoe.text = state.shoeDetail?.sold?.formatSoldShoe()
                    tvRateShoe.text = state.shoeDetail?.rate?.rate.toString()
                    tvReviewShoe.text = state.shoeDetail?.rate?.comments?.size.toString()
                    tvContentDescription.text = state.shoeDetail?.description
                }
            }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.countShoe }
                .collect {
                    binding.tvNumberQuantity.run {
                        setText(it.toString())
                        setSelection(it.toString().length)
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.priceTotal }
                .distinctUntilChanged()
                .collect {
                    binding.tvPriceTotal.text = it.formatPriceShoe()
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.isButtonEnable }
                .distinctUntilChanged()
                .collect {
                    binding.tvButton.isEnabled = it
                }
        }
    }

    override fun setOnClick() {
        binding.run {
            imgBack.setOnClickListener {
                navController?.popBackStack()
            }

            tvNumberQuantity.doAfterTextChanged {
                viewModel.updateCount(it.toString().toIntOrNull() ?: 0)
            }

            tvNumberQuantity.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel.handleEditTextCount()
                }
            }

            imgPlus.setOnClickListener {
                tvNumberQuantity.clearFocus()
                viewModel.handleCountShoe(PLUS)
            }
            imgReduce.setOnClickListener {
                tvNumberQuantity.clearFocus()
                viewModel.handleCountShoe(REDUCE)
            }
        }
    }

    companion object {
        const val REDUCE = 0
        const val PLUS = 1
        const val MAX_SHOE = 999
    }
}