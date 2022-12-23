package com.exfarnanda1945.my_foody_course.ui.fragments.recipes

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.adapter.RecipeAdapter
import com.exfarnanda1945.my_foody_course.databinding.FragmentRecipesBinding
import com.exfarnanda1945.my_foody_course.util.NetworkListener
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
    private val recipeViewModel by activityViewModels<RecipeViewModel>()
    private val mAdapter by lazy { RecipeAdapter() }

    private val args by navArgs<RecipesFragmentArgs>()

    private lateinit var networkListener: NetworkListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        setupSearchMenu()

        binding.apply {
            recipesFab.setOnClickListener {
                if (recipeViewModel.networkStatus) {
                    findNavController().navigate(R.id.action_recipesFragment_to_recipesBottomSheet)
                } else {
                    recipeViewModel.showNetworkStatus()
                }
            }
        }

        recipeViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipeViewModel.backOnline = it
        }

        // start collect internet status when this fragment lifecycle state is created
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkCapabilities(requireContext()).collect { status ->
                recipeViewModel.apply {
                    networkStatus = status
                    showNetworkStatus()
                }
                readDatabase()
            }
        }



        return view
    }

    private fun setupSearchMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)

                val search = menu.findItem(R.id.menu_search)
                val searchView = search.actionView as SearchView

                searchView.apply {
                    isSubmitButtonEnabled = true
                    queryHint = "Search Here"
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            if (query != null) {
                                searchRecipesApi(query)
                            }
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            return true
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observerOnce(viewLifecycleOwner) { database ->
                // if database is not empty and not yet apply from bottom sheet
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
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
                    recipeViewModel.saveMealAndDietType()
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

    private fun searchRecipesApi(query: String) {
        handleShimmerEffect(true)
        mainViewModel.apply {
            searchRecipes(recipeViewModel.applySearchQueries(query))
            searchRecipesResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        handleShimmerEffect(false)
                        response.data?.results.let { mAdapter.setData(it) }
                    }
                    is NetworkResult.Error -> {
                        handleShimmerEffect(false)
                        loadDataFromCache()
                        Toast.makeText(
                            requireContext(),
                            response.msg.toString(),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    is NetworkResult.Loading -> {
                        handleShimmerEffect(true)
                    }
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipe.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe.results)
                }
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