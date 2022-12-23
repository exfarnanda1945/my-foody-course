package com.exfarnanda1945.my_foody_course.ui.fragments.jokes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.databinding.FragmentFoodJokeBinding
import com.exfarnanda1945.my_foody_course.util.Constants.API_KEY
import com.exfarnanda1945.my_foody_course.util.NetworkResult
import com.exfarnanda1945.my_foody_course.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!
    private val mViewModel by activityViewModels<MainViewModel>()
    private var foodJoke = "No Food Joke Here!"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mViewModel

        setupShareMenu()

        mViewModel.apply {
            getFoodJoke(API_KEY)
            foodJokeResponse.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        binding.foodJokeTextView.text = response.data?.text
                        foodJoke = response.data?.text.toString()

                    }
                    is NetworkResult.Error -> {
                        loadFoodJokeFromCache()
                        Toast.makeText(
                            requireContext(),
                            response.msg.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is NetworkResult.Loading -> Log.d("FoodJokeFragment", "loading ")
                }
            }
        }
        return binding.root
    }

    private fun setupShareMenu() {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.share_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if(menuItem.itemId == R.id.share_menu){
                    val shareIntent= Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT,foodJoke)
                        type = "text/plain"
                    }
                    startActivity(shareIntent)
                }
                return true
            }
        },viewLifecycleOwner,Lifecycle.State.STARTED)
    }

    private fun loadFoodJokeFromCache() {
        lifecycleScope.launch {
            mViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (!database.isNullOrEmpty()) {
                    binding.foodJokeTextView.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text.toString()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}