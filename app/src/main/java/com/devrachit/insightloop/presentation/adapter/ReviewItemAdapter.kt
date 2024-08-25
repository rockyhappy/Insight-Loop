package com.devrachit.insightloop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.RvFeedbackItemBinding
import com.devrachit.insightloop.domain.model.Feedback
import com.devrachit.insightloop.domain.model.FeedbackItem

class ReviewItemsAdapter(
    private val items: MutableList<FeedbackItem>,
    private val onAssessmentClick: (Feedback, Int) -> Unit
) : RecyclerView.Adapter<ReviewItemsAdapter.ReviewItemViewHolder>() {

        inner class ReviewItemViewHolder(private val binding: RvFeedbackItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(assessmentItem: FeedbackItem) {
                binding.apply {
                    textAspect.text = assessmentItem.aspect

                    imageNegative.setOnClickListener {
                        cardNegative.setCardBackgroundColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.persian_green
                            )
                        )
                        cardNegative.setCardBackgroundColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.extra_light_gray
                            )
                        )
                        onAssessmentClick(Feedback.ScopeOfImprovement, adapterPosition)
                    }
                    imagePositive.setOnClickListener {
                        cardPositive.setCardBackgroundColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.persian_green
                            )
                        )
                        cardPositive.setCardBackgroundColor(
                            ContextCompat.getColor(
                                it.context,
                                R.color.extra_light_gray
                            )
                        )
                        onAssessmentClick(Feedback.DidWell, adapterPosition)
                    }

                    if (assessmentItem.selectedFeedback == Feedback.DidWell) {
                        cardPositive.setCardBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.persian_green
                            )
                        )
                        cardNegative.setCardBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.extra_light_gray
                            )
                        )
                    } else if (assessmentItem.selectedFeedback == Feedback.ScopeOfImprovement) {
                        cardNegative.setCardBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.persian_green
                            )
                        )
                        cardPositive.setCardBackgroundColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.extra_light_gray
                            )
                        )
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
            return ReviewItemViewHolder(
                RvFeedbackItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
            holder.bind(items[position])
        }
    }