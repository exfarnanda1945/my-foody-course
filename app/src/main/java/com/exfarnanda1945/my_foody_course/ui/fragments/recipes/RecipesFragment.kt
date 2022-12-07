package com.exfarnanda1945.my_foody_course.ui.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.exfarnanda1945.my_foody_course.adapter.RecipeAdapter
import com.exfarnanda1945.my_foody_course.databinding.FragmentRecipesBinding
import com.exfarnanda1945.my_foody_course.util.NetworkResult
import com.exfarnanda1945.my_foody_course.util.observerOnce
import com.exfarnanda1945.my_foody_course.viewmodels.MainViewModel
import com.exfarnanda1945.my_foody_course.viewmodels.RecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {
    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by activityViewModels<MainViewModel>()
    private val recipeViewModel by viewModels<RecipeViewModel>()
    private val mAdapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root

        setupRecyclerView()
        readDatabase()

        return view
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observerOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe.results)
                    handleShimmerEffect(false)
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.recipesRecyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            handleShimmerEffect(true)
        }
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(recipeViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    handleShimmerEffect(false)
                    response.data?.results.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    handleShimmerEffect(false)
                    loadDataFromCache()
                    Toast.makeText(requireContext(), response.msg.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkResult.Loading -> {
                    handleShimmerEffect(true)
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observe(viewLifecycleOwner) { database ->
                mAdapter.setData(database[0].foodRecipe.results)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleShimmerEffect(active: Boolean) {
        if (active) binding.recipesRecyclerview.showShimmer() else binding.recipesRecyclerview.hideShimmer()
    }

}