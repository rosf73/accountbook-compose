package com.woowacamp.android_accountbook_15.ui.tabs.setting

import androidx.compose.runtime.mutableStateListOf
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

    val paymentMethods = mutableStateListOf<PaymentMethod>()
    val expensesCategories = mutableStateListOf<Category>()
    val incomeCategories = mutableStateListOf<Category>()

    val payment = MutableStateFlow<PaymentMethod?>(null)
    val category = MutableStateFlow<Category?>(null)

    init {
        this.paymentMethods.addAll(repository.readAllPaymentMethod().getOrThrow())
        this.expensesCategories.addAll(repository.readAllExpensesCategory().getOrThrow())
        this.incomeCategories.addAll(repository.readAllIncomeCategory().getOrThrow())
    }

    fun insertPaymentMethod(name: String): Long? {
        val res = repository.createPaymentMethod(name).getOrNull()
        if (res == null) {
            createToast("이미 등록된 결제 수단입니다")
            return null
        }
        this.paymentMethods.add(PaymentMethod(res, name))
        return res
    }

    fun insertExpensesCategory(name: String, color: Long): Long? {
        val res = repository.createCategory(0, name, color).getOrNull()
        if (res == null) {
            createToast("이미 등록된 지출 카테고리입니다")
            return null
        }
        this.expensesCategories.add(Category(res, 0, name, color))
        return res
    }

    fun insertIncomeCategory(name: String, color: Long): Long? {
        val res = repository.createCategory(1, name, color).getOrNull()
        if (res == null) {
            createToast("이미 등록된 수입 카테고리입니다")
            return null
        }
        this.incomeCategories.add(Category(res, 1, name, color))
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
            this.paymentMethods.clear()
            this.paymentMethods.addAll(repository.readAllPaymentMethod().getOrThrow())
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
            this.expensesCategories.clear()
            this.incomeCategories.clear()
            this.expensesCategories.addAll(repository.readAllExpensesCategory().getOrThrow())
            this.incomeCategories.addAll(repository.readAllIncomeCategory().getOrThrow())
        }
        category.value = null
    }
}