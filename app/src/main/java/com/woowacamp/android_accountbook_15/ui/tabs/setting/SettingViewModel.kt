package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import com.woowacamp.android_accountbook_15.data.model.Category
import com.woowacamp.android_accountbook_15.data.model.PaymentMethod
import com.woowacamp.android_accountbook_15.utils.createToast
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

    val payment = MutableStateFlow<PaymentMethod?>(null)
    val category = MutableStateFlow<Category?>(null)

    init {
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        val expensesCategories = repository.getAllExpensesCategory().getOrThrow()
        val incomeCategories = repository.getAllIncomeCategory().getOrThrow()
        _state.value = SettingViewState(paymentMethods, expensesCategories, incomeCategories)
    }

    fun insertPaymentMethod(name: String): Long? {
        val res = repository.insertPaymentMethod(name).getOrNull()
        if (res == null) {
            createToast("이미 등록된 결제 수단입니다")
            return null
        }
        val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
        _state.value.paymentMethods = paymentMethods
        return res
    }

    fun insertExpensesCategory(name: String, color: Long): Long? {
        val res = repository.insertCategory(0, name, color).getOrNull()
        if (res == null) {
            createToast("이미 등록된 지출 카테고리입니다")
            return null
        }
        val expensesCategories = repository.getAllExpensesCategory().getOrThrow()
        _state.value.expensesCategories = expensesCategories
        return res
    }

    fun insertIncomeCategory(name: String, color: Long): Long? {
        val res = repository.insertCategory(1, name, color).getOrNull()
        if (res == null) {
            createToast("이미 등록된 수입 카테고리입니다")
            return null
        }
        val incomeCategories = repository.getAllIncomeCategory().getOrThrow()
        _state.value.incomeCategories = incomeCategories
        return res
    }

    fun updatePaymentMethod(name: String) {
        payment.value?.let {
            val res = repository.updatePaymentMethod(it.id, name).getOrNull()
            if (res == null) {
                createToast("이미 등록된 결제 수단입니다")
                payment.value = null
                return
            }
            val paymentMethods = repository.getAllPaymentMethod().getOrThrow()
            _state.value.paymentMethods = paymentMethods
        }
        payment.value = null
    }

    fun updateCategory(name: String, color: Long) {
        category.value?.let {
            val res = repository.updateCategory(it.id, name, color).getOrNull()
            if (res == null) {
                createToast("이미 등록된 카테고리입니다")
                category.value = null
                return
            }
            val expensesCategories = repository.getAllExpensesCategory().getOrThrow()
            val incomeCategories = repository.getAllIncomeCategory().getOrThrow()
            _state.value.expensesCategories = expensesCategories
            _state.value.incomeCategories = incomeCategories
        }
        category.value = null
    }
}

data class SettingViewState(
    var paymentMethods: List<PaymentMethod> = emptyList(),
    var expensesCategories: List<Category> = emptyList(),
    var incomeCategories: List<Category> = emptyList()
)