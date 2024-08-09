package com.fpoly.shoes_app.framework.presentation.ui.favourites

import com.fpoly.shoes_app.databinding.FragmentFavoritesBinding
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment

class FavouritesFragment : BaseFragment<FragmentFavoritesBinding, FavouritesViewModel>(
    FragmentFavoritesBinding::inflate,
    FavouritesViewModel::class.java
) {
    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)
        viewModel.fetchFavourites("")
    }

    override fun bindViewModel() {
        viewModel.favourites.observe(viewLifecycleOwner, { favourites ->
            // Cập nhật UI với danh sách yêu thích
            // Ví dụ: binding.recyclerView.adapter = FavouritesAdapter(favourites)
        })
        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            // Hiển thị thông báo lỗi
            // Ví dụ: Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        })
    }

    override fun setOnClick() {

    }
}