package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.utils.createToast
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

    private val _monthlyHistories = MutableStateFlow(mapOf<String, List<History>>())
    val monthlyHistories: StateFlow<Map<String, List<History>>> get() = _monthlyHistories

    private val _currentYear = MutableStateFlow(getTodayYear())
    val currentYear: StateFlow<Int> get() = _currentYear

    private val _currentMonth = MutableStateFlow(getTodayMonth())
    val currentMonth: StateFlow<Int> get() = _currentMonth

    init {
        _monthlyHistories.value = repository.getMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
    }

    fun setCurrentDate(year: Int, month: Int) {
        _currentYear.value = year
        _currentMonth.value = month
        _monthlyHistories.value = repository.getMonthlyHistories(year, month).getOrThrow()
    }

    fun insertHistory(history: History) {
        val res = repository.insertHistory(
            history.type, history.content, history.date, history.amount, history.payment?.id, history.category?.id
        ).getOrNull()

        if (res == null) {
            createToast("등록에 실패하였습니다")
            return
        }
        _monthlyHistories.value = repository.getMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
    }
}