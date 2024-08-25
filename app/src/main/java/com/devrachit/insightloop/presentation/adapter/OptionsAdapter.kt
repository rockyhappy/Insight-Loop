package com.devrachit.insightloop.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devrachit.insightloop.R
import com.devrachit.insightloop.domain.model.Option
import com.devrachit.insightloop.databinding.RvItemOptionBinding

class OptionAdapter(private val options: MutableList<Option>) : RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {

    inner class OptionViewHolder(private val binding: RvItemOptionBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(option: Option) {
            binding.apply {
                textOption.text = option.text

                if (option.isSelected) {
                    layoutOption.setBackgroundColor(ContextCompat.getColor(root.context, R.color.light_green))
                    textOption.setTextColor(ContextCompat.getColor(root.context, R.color.white))
                } else {
                    layoutOption.setBackgroundColor(ContextCompat.getColor(root.context, R.color.white))
                    textOption.setTextColor(ContextCompat.getColor(root.context, R.color.black))
                }

                layoutOption.setOnClickListener {
                    options[adapterPosition] = options[adapterPosition].copy(isSelected = !options[adapterPosition].isSelected)
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RvItemOptionBinding.inflate(inflater, parent, false)
        return OptionViewHolder(binding)
    }

    override fun getItemCount(): Int = options.size

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.bind(options[position])
    }
}
