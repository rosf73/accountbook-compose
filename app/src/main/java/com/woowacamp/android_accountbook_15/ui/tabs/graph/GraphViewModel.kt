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

    fun setHistories(year: Int, month: Int, categoryId: Long) {
        _historiesEachCategory.value = repository.readHistoriesEachCategory(year, month, categoryId).getOrThrow()
    }
}