package com.woowacamp.android_accountbook_15.ui.tabs.graph

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.woowacamp.android_accountbook_15.R
import com.woowacamp.android_accountbook_15.databinding.FragmentGraphBinding
import com.woowacamp.android_accountbook_15.ui.MainActivity
import com.woowacamp.android_accountbook_15.ui.tabs.history.HistoryViewModel
import com.woowacamp.android_accountbook_15.ui.theme.AndroidAccountBook15Theme

class GraphFragment : Fragment() {

    private var _binding: FragmentGraphBinding? = null
    private val binding: FragmentGraphBinding get() = requireNotNull(_binding)

    private val historyViewModel: HistoryViewModel by activityViewModels()
    private val graphViewModel: GraphViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGraphBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.composeGraph.setContent {
            AndroidAccountBook15Theme {
                GraphScreen(historyViewModel, graphViewModel) {
                    (activity as MainActivity).backToMain()
                }
            }
        }
    }
}