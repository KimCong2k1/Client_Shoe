package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentShoeDetailBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.utility.formatPriceShoe
import com.fpoly.shoes_app.utility.formatQuantityShoe
import com.fpoly.shoes_app.utility.formatReviewShoe
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

    @Inject
    lateinit var sizesAdapter: SizesShoeDetailAdapter

    @Inject
    lateinit var colorsAdapter: ColorsShoeDetailAdapter

    private val args: ShoeDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initShoeDetail(args.idShoe)
    }

    override fun setupViews() {
        binding.run {
            viewPagerImageShoe.adapter = imageShoeDetailAdapter
            binding.dotsIndicator.attachTo(viewPagerImageShoe)

            rcvSize.adapter = sizesAdapter
            rcvColor.adapter = colorsAdapter
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
                sizesAdapter.submitList(state.sizes)
                colorsAdapter.submitList(state.colors)
                binding.run {
                    tvNameShoe.text = state.shoeDetail?.name
                    tvSoldShoe.text = state.sold?.formatSoldShoe()
                    tvRateShoe.text = (state.shoeDetail?.rate?.rate ?: 0.0).toString()
                    tvReviewShoe.text = state.shoeDetail?.rate?.comments?.size?.formatReviewShoe()
                    tvContentDescription.text = state.shoeDetail?.description
                }
            }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.countShoe }
                .collect {
                    binding.run {
                        tvNumberQuantity.setText(it.toString())
                        tvNumberQuantity.setSelection(it.toString().length)
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

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.isCountEnable }
                .collect {
                    binding.run {
                        tvNumberQuantity.isEnabled = it
                        imgReduce.isEnabled = it
                        imgPlus.isEnabled = it
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.uiState
                .mapNotNull { it.sizeStore }
                .distinctUntilChanged()
                .collect {
                    binding.tvQuantity.text = it.formatQuantityShoe()
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

            tvNumberQuantity.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.handleEditTextCount()
                    (requireActivity() as? MainActivity)?.hideKeyboard()
                    tvNumberQuantity.clearFocus()
                    return@setOnEditorActionListener true
                }
                false
            }

            imgPlus.setOnClickListener {
                tvNumberQuantity.clearFocus()
                viewModel.handleCountShoe(PLUS)
            }

            imgReduce.setOnClickListener {
                tvNumberQuantity.clearFocus()
                viewModel.handleCountShoe(REDUCE)
            }

            sizesAdapter.setOnClick {
                viewModel.handleClickSize(it)
            }

            colorsAdapter.setOnClick {
                viewModel.handleClickColor(it)
            }

            tvButton.setOnClickListener {
                viewModel.addShoeToCart()
                showAlertDialog(
                    title = getString(R.string.add_cart_title),
                    button = getString(R.string.add_shoe_to_cart_button),
                    buttonCancel = getString(R.string.cancel_dialog_shoe_to_cart_button),
                    onClick = { navController?.popBackStack() },
                )
            }
        }
    }

    companion object {
        const val REDUCE = 0
        const val PLUS = 1
        const val MAX_SHOE = 999
        const val IS_WHITE_COLOR = "#FFF"
        const val RESET_COUNT_SHOES = 0
    }
}