package com.fpoly.shoes_app.framework.data.datasource

import com.fpoly.shoes_app.framework.domain.model.history.HistoryShoe

interface HistoryDataSource {
    suspend fun getHistory(userId: String): List<HistoryShoe>
}