package com.exfarnanda1945.my_foody_course.ui.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.exfarnanda1945.my_foody_course.databinding.FragmentRecipesBinding


class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recipesRecyclerview.showShimmer()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}