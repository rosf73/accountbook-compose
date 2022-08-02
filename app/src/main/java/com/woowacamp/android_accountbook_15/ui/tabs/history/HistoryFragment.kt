package com.woowacamp.android_accountbook_15.ui.tabs.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.woowacamp.android_accountbook_15.databinding.FragmentHistoryBinding
import com.woowacamp.android_accountbook_15.ui.tabs.setting.SettingViewModel
import com.woowacamp.android_accountbook_15.ui.theme.AndroidAccountBook15Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by activityViewModels<HistoryViewModel>()
    private val settingViewModel by activityViewModels<SettingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeHistory.setContent {
            AndroidAccountBook15Theme {
                HistoryScreen(viewModel, settingViewModel)
            }
        }
    }
}