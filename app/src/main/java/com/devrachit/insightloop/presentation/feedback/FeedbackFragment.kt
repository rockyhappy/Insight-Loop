package com.devrachit.insightloop.presentation.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.FragmentFeedbackBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedbackFragment : Fragment() {
    private lateinit var binding : FragmentFeedbackBinding
    private val viewModel: FeedbackViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        binding.apply {

        }

        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
    }

}