package com.devrachit.insightloop.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.devrachit.insightloop.domain.model.CategoryData
import com.devrachit.insightloop.domain.model.Feedback

class CategoryAdapter (
private val categories: MutableList<CategoryData>,
private val onCategoryFeedbackClick: (Feedback, Int, Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var itemAdapter: CategoryItemsAdapter

        fun bind(categoryItem: CategoryItem) {
            binding.iconCategory.setImageResource(
                when (categoryItem.categoryType) {
                    Category.CONFIDENCE -> R.drawable.confidence
                    Category.GRAMMAR -> R.drawable.grammar
                    Category.FLUENCY_AND_VOCABULARY -> R.drawable.fluency
                    Category.PRONUNCIATION -> R.drawable.pronunciation
                    Category.OTHER -> R.drawable.others
                    Category.UNSPECIFIED -> R.drawable.others
                }
            )

            binding.containerCategory.setOnClickListener {
                if (categories[adapterPosition].isExpanded) {
                    categories[adapterPosition].isExpanded = false
                } else {
                    for (i in categories.indices) {
                        if (i != adapterPosition && categories[i].isExpanded) {
                            categories[i].isExpanded = false
                            notifyItemChanged(i)
                        }
                    }
                    categories[adapterPosition].isExpanded = true
                }
                notifyItemChanged(adapterPosition)
            }

            binding.iconArrow.rotation = if (categoryItem.isExpanded) 90f else 0f

            if (categoryItem.categoryType != Category.OTHER) {
                setupRecyclerView(categoryItem)
            } else {
                binding.feedbackEditText.visibility =
                    if (categoryItem.isExpanded) View.VISIBLE else View.GONE
            }

            binding.titleCategory.text = categoryItem.categoryType.value
            updateFeedbackCount(categoryItem)
        }

        private fun setupRecyclerView(categoryItem: CategoryItem) {
            if (categoryItem.isExpanded) {
                binding.recyclerViewItems.visibility = RecyclerView.VISIBLE
            } else {
                binding.recyclerViewItems.visibility = RecyclerView.GONE
            }
            itemAdapter = CategoryItemsAdapter(categoryItem.items.toMutableList()) { feedback, position ->
                categories[adapterPosition].items[position].selectedFeedback = feedback
                itemAdapter.items[position] = categories[adapterPosition].items[position]
                onCategoryFeedbackClick(feedback, adapterPosition, position)
                updateFeedbackCount(categoryItem)
            }
            binding.recyclerViewItems.apply {
                adapter = itemAdapter
                layoutManager = GridLayoutManager(context, 2)
                clearItemDecorations()
                addItemDecoration(
                    SpacingItemDecoration(2, context.dpToPx(24f), context.dpToPx(24f))
                )
            }
        }

        private fun updateFeedbackCount(categoryItem: CategoryItem) {
            val feedbackCount =
                categoryItem.items.count { it.selectedFeedback != Feedback.NONE }
            binding.feedbackCount.text = feedbackCount.toString()
            binding.feedbackCount.visibility = if (feedbackCount > 0) View.VISIBLE else View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}
}