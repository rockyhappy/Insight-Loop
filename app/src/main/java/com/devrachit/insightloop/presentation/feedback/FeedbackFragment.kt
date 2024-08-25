package com.devrachit.insightloop.presentation.feedback

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.FragmentFeedbackBinding
import com.devrachit.insightloop.domain.model.Feedback
import com.devrachit.insightloop.presentation.adapter.CategoryAdapter
import com.devrachit.insightloop.presentation.bottomsheet.BottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FeedbackFragment : Fragment() {
    private lateinit var binding : FragmentFeedbackBinding
    private val viewModel: FeedbackViewModel by viewModels()
    private lateinit var feedbackCategoryAdapter: CategoryAdapter
    private lateinit var optionBottomSheet: BottomSheet
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedbackBinding.inflate(inflater, container, false)
        binding.apply {

        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(viewModel.submitEnabled.value){
            enableSubmitButton()
        }
        else{
            disableSubmitButton()
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.feedbackCategories.collectLatest {

                    feedbackCategoryAdapter =
                        CategoryAdapter(it.toMutableList()) { feedback, i, j ->

                            val categories = feedbackCategoryAdapter.categories
                            when (feedback) {
                                Feedback.DidWell -> {
                                    if (categories[i].feedbackItems[j].didWell.size > 1) {
                                        optionBottomSheet =
                                            BottomSheet(categories[i].feedbackItems[j].didWell) {
                                                feedbackCategoryAdapter.categories[i].feedbackItems[j].didWell =
                                                    it
                                            }
                                        optionBottomSheet.show(parentFragmentManager, "BottomSheet")
                                    }
                                }

                                Feedback.ScopeOfImprovement -> {
                                    if (categories[i].feedbackItems[j].scopeOfImprovement.size > 1) {
                                        optionBottomSheet =
                                            BottomSheet(categories[i].feedbackItems[j].scopeOfImprovement) {
                                                feedbackCategoryAdapter.categories[i].feedbackItems[j].scopeOfImprovement =
                                                    it
                                            }
                                        optionBottomSheet.show(parentFragmentManager, "BottomSheet")
                                    }
                                }

                                Feedback.None-> {}
                            }

                            for (k in 0 until feedbackCategoryAdapter.categories.size - 1) {
                                val item = feedbackCategoryAdapter.categories[k]
                                if (item.feedbackItems.count { it.selectedFeedback != Feedback.None } == 0) {
                                    viewModel.setEnabled(false)
                                    disableSubmitButton()
                                } else {
                                    viewModel.setEnabled(true)
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
                R.color.light_green
            )
        )
    }

    private fun disableSubmitButton(){
        binding.btnSubmit.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.extra_light_green
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
        binding.root.removeAllViewsInLayout()
    }

}