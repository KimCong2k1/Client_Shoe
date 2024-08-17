package com.fpoly.shoes_app.framework.data.datasource.active

import com.fpoly.shoes_app.framework.data.dataremove.api.getInterface.OrderApiService
import com.fpoly.shoes_app.framework.data.dataremove.api.getInterface.OrderRepository
import com.fpoly.shoes_app.framework.domain.model.history.HistoryShoe

class OrderRepositoryImpl(
    private val orderApiService: OrderApiService
) : OrderRepository {
    override suspend fun getActiveOrders(userId: String): List<HistoryShoe> {
        return orderApiService.getActiveOrders(userId)
    }

    override suspend fun getCompletedOrders(userId: String): List<HistoryShoe> {
        return orderApiService.getCompletedOrders(userId)
    }

}