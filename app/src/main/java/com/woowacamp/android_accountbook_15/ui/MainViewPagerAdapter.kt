package com.woowacamp.android_accountbook_15.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woowacamp.android_accountbook_15.ui.tabs.calendar.CalendarFragment
import com.woowacamp.android_accountbook_15.ui.tabs.graph.GraphFragment
import com.woowacamp.android_accountbook_15.ui.tabs.history.HistoryFragment
import com.woowacamp.android_accountbook_15.ui.tabs.setting.SettingFragment

private const val TAB_COUNT = 4

class MainViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryFragment()
            1 -> CalendarFragment()
            2 -> GraphFragment()
            else -> SettingFragment()
        }
    }
}