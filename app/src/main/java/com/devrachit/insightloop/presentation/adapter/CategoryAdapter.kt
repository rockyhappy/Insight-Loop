package com.devrachit.insightloop.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devrachit.insightloop.R
import com.devrachit.insightloop.data.remote.mapper.toCategory
import com.devrachit.insightloop.databinding.RvCategoryItemBinding
import com.devrachit.insightloop.domain.model.Category
import com.devrachit.insightloop.domain.model.CategoryData
import com.devrachit.insightloop.domain.model.Feedback

class CategoryAdapter (
 val categories: MutableList<CategoryData>,
private val onCategoryFeedbackClick: (Feedback, Int, Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    inner class CategoryViewHolder(private val binding: RvCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var itemAdapter: ReviewItemsAdapter

        fun bind(categoryItem: CategoryData) {
            binding.ivCategoryIcon.setImageResource(
                when (categoryItem.category.toCategory()) {
                    Category.CONFIDENCE -> R.drawable.ic_confidence
                    Category.GRAMMAR -> R.drawable.ic_grammar
                    Category.FLUENCY_AND_VOCABULARY -> R.drawable.ic_fluency
                    Category.PRONUNCIATION -> R.drawable.ic_pronunciation
                    Category.OTHER -> R.drawable.ic_others
                    Category.UNSPECIFIED -> R.drawable.ic_others
                }
            )

            binding.llCategory.setOnClickListener {
                if (categories[adapterPosition].isOpen) {
                    categories[adapterPosition].isOpen = false
                } else {
                    for (i in categories.indices) {
                        if (i != adapterPosition && categories[i].isOpen) {
                            categories[i].isOpen = false
                            notifyItemChanged(i)
                        }
                    }
                    categories[adapterPosition].isOpen = true
                }
                notifyItemChanged(adapterPosition)
            }
            
            if(categoryItem.isOpen){
                binding.ivArrow.rotation = 90f
            }
            else{
                binding.ivArrow.rotation = 0f
            }
            if (categoryItem.category.toCategory() != Category.OTHER) {
                if (categoryItem.isOpen) {
                    binding.rvFeedbackItems.visibility = RecyclerView.VISIBLE
                } else {
                    binding.rvFeedbackItems.visibility = RecyclerView.GONE
                }
                itemAdapter =
                    ReviewItemsAdapter(categoryItem.feedbackItems.toMutableList()) { feedback, position ->
                        categories[adapterPosition].feedbackItems[position].selectedFeedback = feedback
                        itemAdapter.items[position] = categories[adapterPosition].feedbackItems[position]
                        onCategoryFeedbackClick(feedback, adapterPosition, position)
                        val feedbackCount =
                            categoryItem.feedbackItems.count { it.selectedFeedback != Feedback.None }
                        if (feedbackCount > 0) {
                            binding.tvFeedbackCount.text = feedbackCount.toString()
                            binding.tvFeedbackCount.visibility = View.VISIBLE
                        } else {
                            binding.tvFeedbackCount.visibility = View.GONE
                        }
                    }
                binding.rvFeedbackItems.adapter = itemAdapter
                binding.rvFeedbackItems.layoutManager =
                    GridLayoutManager(binding.root.context, 2, GridLayoutManager.VERTICAL, false)
                binding.etFeedback.visibility = View.GONE
            } else {
                binding.rvFeedbackItems.visibility = View.GONE
                if (categoryItem.isOpen) {
                    binding.etFeedback.visibility = RecyclerView.VISIBLE
                } else {
                    binding.etFeedback.visibility = RecyclerView.GONE
                }
            }
            binding.tvCategoryTitle.text = categoryItem.category
            val feedbackCount =
                categoryItem.feedbackItems.count { it.selectedFeedback != Feedback.None }
            if (feedbackCount > 0) {
                binding.tvFeedbackCount.text = feedbackCount.toString()
                binding.tvFeedbackCount.visibility = View.VISIBLE
            } else {
                binding.tvFeedbackCount.visibility = View.GONE
            }
        }


        }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            RvCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }
}
