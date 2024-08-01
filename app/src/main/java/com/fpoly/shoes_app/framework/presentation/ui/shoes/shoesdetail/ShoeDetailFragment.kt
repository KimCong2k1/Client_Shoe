package com.fpoly.shoes_app.framework.presentation.ui.shoes.shoesdetail

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.fpoly.shoes_app.databinding.FragmentShoeDetailBinding
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.utility.formatSoldShoe
import dagger.hilt.android.AndroidEntryPoint
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
        binding.viewPagerImageShoe.adapter = imageShoeDetailAdapter
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect{ state ->
                imageShoeDetailAdapter.images = state.shoeDetail?.imagesShoe ?: emptyList()
                binding.run {
                    tvNameShoe.text = state.shoeDetail?.name
                    tvSoldShoe.text = state.shoeDetail?.sold?.formatSoldShoe()
                    tvRateShoe.text = state.shoeDetail?.rate?.rate.toString()
                    tvReviewShoe.text = state.shoeDetail?.rate?.comments?.size.toString()
                    tvContentDescription.text = state.shoeDetail?.description
                }
            }
        }
    }

    override fun setOnClick() {

    }
}