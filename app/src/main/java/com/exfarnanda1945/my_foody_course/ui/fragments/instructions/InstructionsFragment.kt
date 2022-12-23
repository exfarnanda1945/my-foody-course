package com.exfarnanda1945.my_foody_course.ui.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.exfarnanda1945.my_foody_course.databinding.FragmentInstructionsBinding
import com.exfarnanda1945.my_foody_course.model.ResultsItem
import com.exfarnanda1945.my_foody_course.util.Constants
import com.exfarnanda1945.my_foody_course.util.parcelable


class InstructionsFragment : Fragment() {
    private var _binding: FragmentInstructionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle = args?.parcelable<ResultsItem>(Constants.RECIPE_BUNDLE_KEY)

        binding.instructionWebView.apply {
            webViewClient = object : WebViewClient() {}
            resultBundle?.sourceUrl?.let {
                loadUrl(it)
            }

        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}