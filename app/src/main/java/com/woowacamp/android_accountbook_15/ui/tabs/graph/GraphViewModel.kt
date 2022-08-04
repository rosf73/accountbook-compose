package com.woowacamp.android_accountbook_15.ui.tabs.graph

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.History
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class GraphViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

    private val _historiesEachCategory = MutableStateFlow(mapOf<String, List<History>>())
    val historiesEachCategory: StateFlow<Map<String, List<History>>> get() = _historiesEachCategory

    private val _monthlyTotalAmountEachCategory = MutableStateFlow(listOf<Pair<Int, Int>>())
    val monthlyTotalAmountEachCategory: StateFlow<List<Pair<Int, Int>>> get() = _monthlyTotalAmountEachCategory

    fun setHistories(year: Int, month: Int, categoryId: Long) {
        val startYear = if (month-5 > 0) year else year-1
        val startMonth = if (month-5 > 0) month-5 else month+12-5
        _historiesEachCategory.value = repository.readHistoriesEachCategory(year, month, categoryId).getOrThrow()
        _monthlyTotalAmountEachCategory.value = repository.readMonthlyTotalAmount(startYear, startMonth, year, month, categoryId).getOrThrow()
    }
}