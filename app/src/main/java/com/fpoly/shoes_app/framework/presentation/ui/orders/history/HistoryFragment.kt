package com.fpoly.shoes_app.framework.presentation.ui.orders.history

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentHistoryBinding
import com.fpoly.shoes_app.framework.adapter.order.HistoryAdapter
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.orders.OrdersFragment
import com.fpoly.shoes_app.framework.presentation.ui.orders.OrdersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : BaseFragment<FragmentHistoryBinding, OrdersViewModel>(
    FragmentHistoryBinding::inflate, OrdersViewModel::class.java
) {
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var idUser: String

    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)
        idUser = sharedPreferences.getIdUser()
        viewModel.getCompletedOrders("6695522b49fed05c4c9c6990")
        historyAdapter = HistoryAdapter(emptyList()) { history ->
            val bundle = Bundle().apply {
                putParcelable("history", history)
            }
            findNavController().navigate(R.id.detailHistoryFragment, bundle)
        }

        binding.recycViewHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = historyAdapter
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.isLoading) {
                } else {
                    historyAdapter.updateHistoryShoes(state.historyShoes)
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