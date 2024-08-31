package com.devrachit.insightloop.presentation.adapter

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.FeedbackItemBinding
import com.devrachit.insightloop.domain.model.Feedback
import com.devrachit.insightloop.domain.model.FeedbackItem

class FeedbackItemsAdapter(
    val list: MutableList<FeedbackItem>,
    private val onFeedbackClick: (Feedback, Int) -> Unit
) :
    RecyclerView.Adapter<FeedbackItemsAdapter.FeedbackItemViewHolder>() {
    inner class FeedbackItemViewHolder(private val binding: FeedbackItemBinding) :
        RecyclerView.ViewHolder( binding.root) {
        fun bind(feedbackItem: FeedbackItem) {
            binding.apply {
                tvAspect.text = feedbackItem.aspect
                cvNegative.setOnClickListener {
                    cvNegative.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.persian_green
                        )
                    )
                    cvPositive.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.gray_extra_light
                        )
                    )
                    onFeedbackClick(Feedback.SCOPE_OF_IMPROVEMENT, adapterPosition)
                }
                cvPositive.setOnClickListener {
                    cvPositive.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.persian_green
                        )
                    )
                    cvNegative.setCardBackgroundColor(
                        ContextCompat.getColor(
                            it.context,
                            R.color.gray_extra_light
                        )
                    )
                    onFeedbackClick(Feedback.DID_WELL, adapterPosition)
                }

                if (feedbackItem.selectedFeedback == Feedback.DID_WELL) {
                    cvPositive.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.persian_green
                        )
                    )
                    cvNegative.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.gray_extra_light
                        )
                    )
                }

                if (feedbackItem.selectedFeedback == Feedback.SCOPE_OF_IMPROVEMENT) {
                    cvPositive.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.gray_extra_light
                        )
                    )
                    cvNegative.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.persian_green
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackItemViewHolder {
        return FeedbackItemViewHolder(FeedbackItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FeedbackItemViewHolder, position: Int) {
        holder.bind(list[position])
    }
}
class FeedbackSpacingItemDecoration(private val spanCount : Int, private val horizontalSpacing: Int, private val verticalSpacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount

        outRect.left = column * horizontalSpacing / spanCount
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount
        if (position >= spanCount) {
            outRect.top = verticalSpacing
        }
    }
}