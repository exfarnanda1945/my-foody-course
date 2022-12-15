package com.exfarnanda1945.my_foody_course.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exfarnanda1945.my_foody_course.databinding.FragmentOverviewBinding
import com.exfarnanda1945.my_foody_course.model.ResultsItem
import com.exfarnanda1945.my_foody_course.util.Constants.RECIPE_BUNDLE_KEY
import com.exfarnanda1945.my_foody_course.util.parcelable

class OverviewFragment : Fragment() {
    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle = args?.parcelable<ResultsItem>(RECIPE_BUNDLE_KEY)

        binding.result = resultBundle

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}