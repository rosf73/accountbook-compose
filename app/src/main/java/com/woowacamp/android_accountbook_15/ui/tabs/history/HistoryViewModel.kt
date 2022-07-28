package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.utils.getTodayMonth
import com.woowacamp.android_accountbook_15.utils.getTodayYear
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

    private val _histories = MutableStateFlow(
        repository.getMonthlyHistories(getTodayYear(), getTodayMonth()).getOrThrow()
    )
    val histories: StateFlow<List<History>> get() = _histories
}