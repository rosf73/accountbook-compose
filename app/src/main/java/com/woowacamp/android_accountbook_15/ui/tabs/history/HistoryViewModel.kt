package com.woowacamp.android_accountbook_15.ui.tabs.history

import androidx.lifecycle.ViewModel
import com.woowacamp.android_accountbook_15.data.AccountBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: AccountBookRepository
): ViewModel() {

}