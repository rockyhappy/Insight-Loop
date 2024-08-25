package com.devrachit.insightloop.presentation.thankyou

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devrachit.insightloop.R
import com.devrachit.insightloop.databinding.FragmentThankyouBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThankyouFragment : Fragment() {
    private lateinit var binding : FragmentThankyouBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentThankyouBinding.inflate(inflater, container, false)
        binding.apply {

        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
    }


}