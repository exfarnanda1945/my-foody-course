package com.exfarnanda1945.my_foody_course.ui.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.exfarnanda1945.my_foody_course.adapter.IngredientsAdapter
import com.exfarnanda1945.my_foody_course.databinding.FragmentIngredientsBinding
import com.exfarnanda1945.my_foody_course.model.ResultsItem
import com.exfarnanda1945.my_foody_course.util.Constants
import com.exfarnanda1945.my_foody_course.util.parcelable

class IngredientsFragment : Fragment() {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!

    private val ingredientAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIngredientsBinding.inflate(inflater, container, false)

        val args = arguments
        val resultBundle = args?.parcelable<ResultsItem>(Constants.RECIPE_BUNDLE_KEY)

        resultBundle?.extendedIngredients?.let {
            ingredientAdapter.setData(it)
        }

        setupRecyclerView()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.ingredientRecyclerView.apply {
            adapter = ingredientAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}