package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.History
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
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

    val history = MutableStateFlow<History?>(null)

    val selectedHistories = mutableStateListOf<Long>()

    init {
        _monthlyHistories.value = repository.readMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
    }

    fun setCurrentDate(year: Int, month: Int) {
        _currentYear.value = year
        _currentMonth.value = month
        _monthlyHistories.value = repository.readMonthlyHistories(year, month).getOrThrow()
    }

    fun getExpensesEachCategory(): Map<Category, Float> {
        return mutableMapOf<Category, Float>().apply {
            monthlyHistories.value.forEach { (_, value) ->
                value.forEach { history ->
                    if (history.type != 1)
                        if (containsKey(history.category))
                            this[history.category]?.plus(history.amount)
                        else
                            this[history.category] = history.amount.toFloat()
                }
            }
        }
    }

    fun insertHistory(history: History) {
        val res = repository.insertHistory(
            history.type, history.content, history.date, history.amount, history.payment?.id, history.category.id
        ).getOrNull()

        if (res == null) {
            createToast("등록에 실패하였습니다")
            return
        }
        _monthlyHistories.value = repository.readMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
    }

    fun updateHistory(newHistory: History) {
        history.value?.let {
            val res = repository.updateHistory(
                it.id,
                newHistory.type,
                newHistory.content,
                newHistory.amount,
                newHistory.date,
                newHistory.payment,
                newHistory.category
            ).getOrNull()

            if (res == null) {
                createToast("등록에 실패하였습니다")
                return
            }
            _monthlyHistories.value = repository.readMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
        }
        history.value = null
    }

    fun removeHistories() {
        val res = repository.deleteHistories(selectedHistories).getOrNull()

        if (res == null) {
            createToast("삭제에 실패하였습니다")
            return
        }
        _monthlyHistories.value = repository.readMonthlyHistories(currentYear.value, currentMonth.value).getOrThrow()
        removeAllSelectedHistories()
    }

    fun selectHistory(id: Long) {
        selectedHistories.add(id)
    }

    fun deselectHistory(id: Long) {
        selectedHistories.remove(id)
    }

    fun removeAllSelectedHistories() {
        selectedHistories.clear()
    }
}