package com.devrachit.insightloop.presentation.feedback

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.FragmentHomeBinding
import com.devrachit.insightloop.domain.model.Feedback
import com.devrachit.insightloop.presentation.adapter.FeedbackCategoryAdapter
import com.devrachit.insightloop.presentation.bottomsheet.BottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedbackFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FeedbackViewModel by viewModels()
    private lateinit var feedbackCategoryAdapter: FeedbackCategoryAdapter

    private lateinit var bottomSheet: BottomSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            if (viewModel.submitEnabled) {
                findNavController().navigate(R.id.action_feedbackFragment_to_thankYouFragment)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.submitEnabled){
            enableSubmitButton()
        }
        else{
            disableSubmitButton()
        }

        binding.btnSubmit.setOnClickListener {
            viewModel.makeRequest()
            lifecycleScope.launch {
                viewModel.feedbackSubmittedFlow.collect{isSubmitted->
                    if(isSubmitted){
                        findNavController().navigate(R.id.action_feedbackFragment_to_thankYouFragment)
                    }

                }
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.feedbackCategories.collectLatest {

                    feedbackCategoryAdapter =
                        FeedbackCategoryAdapter(it.toMutableList()) { feedback, i, j ->

                            val list = feedbackCategoryAdapter.list
                            when (feedback) {
                                Feedback.DID_WELL -> {
                                    if (list[i].feedbackItems[j].didWell.size > 1) {
                                        bottomSheet =
                                            BottomSheet(list[i].feedbackItems[j].didWell) {
                                                feedbackCategoryAdapter.list[i].feedbackItems[j].didWell =
                                                    it
                                            }
                                        bottomSheet.show(parentFragmentManager, "BottomSheet")
                                    }
                                }

                                Feedback.SCOPE_OF_IMPROVEMENT -> {
                                    if (list[i].feedbackItems[j].scopeOfImprovement.size > 1) {
                                        bottomSheet =
                                            BottomSheet(list[i].feedbackItems[j].scopeOfImprovement) {
                                                feedbackCategoryAdapter.list[i].feedbackItems[j].scopeOfImprovement =
                                                    it
                                            }
                                        bottomSheet.show(parentFragmentManager, "BottomSheet")
                                    }
                                }

                                Feedback.NONE -> {}
                            }

                            for (k in 0 until feedbackCategoryAdapter.list.size - 1) {
                                val item = feedbackCategoryAdapter.list[k]
                                if (item.feedbackItems.count { it.selectedFeedback != Feedback.NONE } == 0) {
                                    viewModel.setSubmitEnabled(false)
                                    disableSubmitButton()
                                } else {
                                    viewModel.setSubmitEnabled(true)
                                    enableSubmitButton()
                                }
                            }

                        }

                    binding.rvFeedback.adapter = feedbackCategoryAdapter
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collectLatest {
                    if (it) {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.rvFeedback.visibility = View.GONE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.rvFeedback.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun enableSubmitButton() {
        binding.btnSubmit.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        binding.btnSubmit.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.green_light
            )
        )
    }

    private fun disableSubmitButton(){
        binding.btnSubmit.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.green_extra_light
            )
        )
        binding.btnSubmit.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.light_green
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}