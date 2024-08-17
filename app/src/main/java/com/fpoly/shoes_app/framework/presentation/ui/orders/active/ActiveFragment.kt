package com.fpoly.shoes_app.framework.presentation.ui.orders.active

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.FragmentActiveBinding
import com.fpoly.shoes_app.databinding.FragmentOrdersBinding
import com.fpoly.shoes_app.framework.adapter.order.HistoryAdapter
import com.fpoly.shoes_app.framework.presentation.MainActivity
import com.fpoly.shoes_app.framework.presentation.common.BaseFragment
import com.fpoly.shoes_app.framework.presentation.ui.orders.OrdersViewModel
import com.fpoly.shoes_app.framework.presentation.ui.orders.history.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ActiveFragment : BaseFragment<FragmentActiveBinding, OrdersViewModel>(
    FragmentActiveBinding::inflate,
    OrdersViewModel::class.java
) {  private lateinit var activeAdapter: HistoryAdapter
    private lateinit var idUser: String

    override fun setupViews() {
        (requireActivity() as MainActivity).showBottomNavigation(true)
//        idUser = sharedPreferences.getIdUser()
        viewModel.getActiveOrders("6695522b49fed05c4c9c6990")
        activeAdapter = HistoryAdapter(emptyList()) { history ->
            val bundle = Bundle().apply {
                putParcelable("history", history)
            }
            findNavController().navigate(R.id.detailHistoryFragment, bundle)
        }

        binding.recycViewHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = activeAdapter
        }
    }

    override fun bindViewModel() {
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.isLoading) {
                } else {
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

    }}