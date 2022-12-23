package com.exfarnanda1945.my_foody_course.ui.fragments.favorites

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.adapter.FavoriteRecipesAdapter
import com.exfarnanda1945.my_foody_course.databinding.FragmentFavoriteRecipesBinding
import com.exfarnanda1945.my_foody_course.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : Fragment() {
    private var _binding: FragmentFavoriteRecipesBinding? = null
    private val binding get() = _binding!!

    private val mViewModel by activityViewModels<MainViewModel>()
    private val mAdapter by lazy {
        FavoriteRecipesAdapter(requireActivity(), mViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.mAdapter = mAdapter
        binding.mainViewModel = mViewModel

        setupRecyclerView()
        setupMenu()

        return binding.root
    }

    private fun setupMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.favorite_delete_all_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.deleteAll_favorite_recipe_menu) {
                    mViewModel.deleteAllFavoriteRecipes()
                    Snackbar.make(binding.root, "All recipes removed.", Snackbar.LENGTH_SHORT)
                        .show()
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        binding.favoriteRecipeRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.clearContextualActionMode()
    }
}