package com.woowacamp.android_accountbook_15.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }

    private fun initNavigation() {
        val tabTitleArray = arrayOf(
            getString(R.string.history_tab),
            getString(R.string.calendar_tab),
            getString(R.string.graph_tab),
            getString(R.string.setting_tab)
        )
        val tabIconArray = arrayOf(
            R.drawable.ic_history,
            R.drawable.ic_calendar,
            R.drawable.ic_graph,
            R.drawable.ic_setting
        )

        binding.viewpagerMain.adapter = MainViewPagerAdapter(supportFragmentManager, lifecycle)

        TabLayoutMediator(binding.tablayoutMain, binding.viewpagerMain) { tab, pos ->
            tab.text = tabTitleArray[pos]
            tab.icon = ContextCompat.getDrawable(this, tabIconArray[pos])
        }.attach()
    }

    fun backToMain() {
        _binding?.let {
            if (binding.viewpagerMain.currentItem > 0) {
                binding.viewpagerMain.currentItem = 0
            }
        }
    }
}