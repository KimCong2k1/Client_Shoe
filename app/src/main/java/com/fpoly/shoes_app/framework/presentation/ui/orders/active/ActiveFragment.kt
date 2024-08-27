package com.fpoly.shoes_app.framework.presentation.ui.orders.active

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentActiveBinding
import com.fpoly.shoes_app.framework.adapter.order.HistoryAdapter
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ActiveFragment : BaseFragment<FragmentActiveBinding, OrdersViewModel>(
    FragmentActiveBinding::inflate,
    OrdersViewModel::class.java
) {
    private lateinit var activeAdapter: HistoryAdapter
    private lateinit var idUser: String

    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)
        idUser = sharedPreferences.getIdUser()
        activeAdapter = HistoryAdapter(
            historyShoes = emptyList(), onClickActive = { active ->
                val bundle = Bundle().apply {
                    putParcelable("history", active)
                }
                findNavController().navigate(R.id.detailActiveFragment, bundle)
            }, onClickComplete = null
        )
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                viewModel.getActiveOrders("6673eb34167c5a3c87932cc7")
            }
            recycViewHistory.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = activeAdapter
            }
        }
        viewModel.getActiveOrders("6673eb34167c5a3c87932cc7")

    }
    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                binding.swipeRefreshLayout.isRefreshing = state.isLoading
                if (!state.isLoading) {
                    activeAdapter.updateHistoryShoes(state.historyShoes)
                    state.errorMessage?.let { showMessage(it) }
                }
            }
        }
    }
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun setOnClick() {
    }
}