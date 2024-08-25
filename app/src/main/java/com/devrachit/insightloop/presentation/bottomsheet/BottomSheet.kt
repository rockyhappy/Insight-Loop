package com.devrachit.insightloop.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devrachit.insightloop.databinding.BottomsheetBinding
import com.devrachit.insightloop.domain.model.Option
import com.devrachit.insightloop.presentation.adapter.OptionAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    private val options:List<Option>,
    private val onDoneClick: (List<Option>) -> Unit
    ): BottomSheetDialogFragment(){

    private lateinit var binding: BottomsheetBinding

    private val adapter by lazy {
        OptionAdapter(options.toMutableList())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetBinding.inflate(inflater, container, false)

        binding.apply{
            rvOptions.adapter = adapter

            btnCross.setOnClickListener{
                dismiss()
            }

            btnDone.setOnClickListener {
                onDoneClick(adapter.list)
                dismiss()
            }
        }

        return binding.root
    }
}