package com.woowacamp.android_accountbook_15.ui.tabs.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.Fragment
import com.woowacamp.android_accountbook_15.databinding.FragmentSettingBinding
import com.woowacamp.android_accountbook_15.ui.theme.AndroidAccountBook15Theme

class SettingFragment: Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeContainer.setContent {
            AndroidAccountBook15Theme {
                SettingView()
            }
        }
    }
}