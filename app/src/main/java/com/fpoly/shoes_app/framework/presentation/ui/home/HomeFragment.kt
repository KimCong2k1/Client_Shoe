package com.fpoly.shoes_app.framework.presentation.ui.home

import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fpoly.shoes_app.databinding.FragmentHomeBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.categories.CategoriesAdapter
import com.fpoly.shoes_app.framework.presentation.ui.categories.CategoriesSelectedAdapter
import com.fpoly.shoes_app.framework.presentation.ui.shoes.ShoesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate,
    HomeViewModel::class.java
) {

    @Inject
    lateinit var categoriesAdapter: CategoriesAdapter

    @Inject
    lateinit var categoriesSelectedAdapter: CategoriesSelectedAdapter

    @Inject
    lateinit var shoesAdapter: ShoesAdapter

    override fun setupViews() {
        (requireActivity() as? MainActivity)?.showBottomNavigation(true)
        setupRecyclerView()
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                categoriesAdapter.submitList(state.categories)
                categoriesSelectedAdapter.submitList(state.categoriesSelected)
            }
        }

        lifecycleScope.launch {
            viewModel.uiState.mapNotNull {
                it.shoes
            }.distinctUntilChanged().collect {
                shoesAdapter.submitList(it)
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
        setOnClickCategorySelected()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rcvCategory.run {
                layoutManager = StaggeredGridLayoutManager(
                    SPAN_COUNT_CATEGORIES,
                    StaggeredGridLayoutManager.VERTICAL
                )
                adapter = categoriesAdapter
            }
            rcvCategoriesSelected.adapter = categoriesSelectedAdapter
            rcvShoes.adapter = shoesAdapter
        }
    }

    private fun setOnClickCategorySelected() {
        categoriesSelectedAdapter.setOnClick {
            viewModel.handleClickCategoriesSelected(it)
        }
    }

    private companion object {
        private const val SPAN_COUNT_CATEGORIES = 4
    }
}