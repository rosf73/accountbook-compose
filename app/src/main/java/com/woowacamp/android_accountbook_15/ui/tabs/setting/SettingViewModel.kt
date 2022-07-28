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

    private val _paymentMethods = MutableStateFlow(repository.getAllPaymentMethod().getOrThrow())
    val paymentMethods: StateFlow<List<PaymentMethod>> get() = _paymentMethods

    init {
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        _state.value = SettingViewState(paymentMethods)
    }

    fun getSettingStates() {
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        _state.value = SettingViewState(paymentMethods)
    }

    fun insertPaymentMethod(name: String) {

    }

    fun insertExpensesCategory(name: String, color: String) {

    }
}

data class SettingViewState(
    val paymentMethods: List<PaymentMethod> = emptyList(),
    val incomeCategories: List<Category> = emptyList(),
    val expensesCategories: List<Category> = emptyList()
)