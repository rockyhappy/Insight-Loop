package com.devrachit.insightloop.presentation.bottomsheet

import com.devrachit.insightloop.domain.model.Option
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheet(
    private val options:List<Option>,
    private val onDoneClick: (List<Option>) -> Unit
    ): BottomSheetDialogFragment(){

    private lateinit var binding: BottomSheetBinding
}