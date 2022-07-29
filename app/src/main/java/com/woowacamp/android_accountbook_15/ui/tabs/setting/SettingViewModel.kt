package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

    private val _state = MutableStateFlow(SettingViewState())
    val state: StateFlow<SettingViewState> get() = _state

    init {
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        val expensesCategories = repository.getAllExpensesCategory().getOrThrow()
        val incomeCategories = repository.getAllIncomeCategory().getOrThrow()
        _state.value = SettingViewState(paymentMethods, expensesCategories, incomeCategories)
    }

    fun insertPaymentMethod(name: String) {
        repository.insertPaymentMethod(name)
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        _state.value.paymentMethods = paymentMethods
    }

    fun insertExpensesCategory(name: String, color: Long) {
        repository.insertCategory(0, name, color)
        val expensesCategories = repository.getAllExpensesCategory().getOrThrow()
        _state.value.expensesCategories = expensesCategories
    }

    fun insertIncomeCategory(name: String, color: Long) {
        repository.insertCategory(1, name, color)
        val incomeCategories = repository.getAllIncomeCategory().getOrThrow()
        _state.value.incomeCategories = incomeCategories
    }
}

data class SettingViewState(
    var paymentMethods: List<PaymentMethod> = emptyList(),
    var expensesCategories: List<Category> = emptyList(),
    var incomeCategories: List<Category> = emptyList()
)