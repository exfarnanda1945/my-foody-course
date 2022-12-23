package com.exfarnanda1945.my_foody_course.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.adapter.PagerAdapter
import com.exfarnanda1945.my_foody_course.data.database.entities.FavoriteRecipesEntity
import com.exfarnanda1945.my_foody_course.databinding.ActivityDetailsBinding
import com.exfarnanda1945.my_foody_course.ui.fragments.ingredients.IngredientsFragment
import com.exfarnanda1945.my_foody_course.ui.fragments.instructions.InstructionsFragment
import com.exfarnanda1945.my_foody_course.ui.fragments.overview.OverviewFragment
import com.exfarnanda1945.my_foody_course.util.Constants.RECIPE_BUNDLE_KEY
import com.exfarnanda1945.my_foody_course.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var adapterViewPager: PagerAdapter

    private val args by navArgs<DetailsActivityArgs>()
    private val mViewModel by viewModels<MainViewModel>()

    private val yellowColor: Int by lazy {
        R.color.yellow
    }
    private val whiteColor: Int by lazy {
        R.color.white
    }

    private var recipesSaved = false
    private var recipeSavedId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)


        binding.toolbar.apply {
            setSupportActionBar(this)
            setTitleTextColor(ContextCompat.getColor(this@DetailsActivity, R.color.white))
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_BUNDLE_KEY, args.result)

        adapterViewPager = PagerAdapter(supportFragmentManager, lifecycle, resultBundle)
        setupTabLayout()
        setContentView(binding.root)
    }

    private fun setupTabLayout() {
        adapterViewPager.addFragment(OverviewFragment(), "Overview")
        adapterViewPager.addFragment(IngredientsFragment(), "Ingredients")
        adapterViewPager.addFragment(InstructionsFragment(), "Instruction")

        binding.viewPager.apply {
            isUserInputEnabled = false
            adapter = adapterViewPager
            currentItem = 0
        }

        TabLayoutMediator(binding.tabBarLayout, binding.viewPager) { tab, position ->
            tab.text = adapterViewPager.getTitleTab(position)
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favorite_recipes)
        checkedSavedRecipes(menuItem!!)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkedSavedRecipes(menuItem: MenuItem) {
        mViewModel.readFavoriteRecipes.observe(this) { favorites ->
            val findFavorite = favorites.find {
                it.result.id == args.result.id
            }
            if (findFavorite !== null) {
                changeMenuItemColor(menuItem, yellowColor)
                recipesSaved = true
                recipeSavedId = findFavorite.id
            } else {
                changeMenuItemColor(menuItem, whiteColor)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favorite_recipes) {
            if (!recipesSaved) {
                saveFavoriteRecipes(item)
            } else {
                deleteFavoriteRecipes(item)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveFavoriteRecipes(item: MenuItem) {
        val favoriteRecipesEntity = FavoriteRecipesEntity(0, args.result)
        mViewModel.insertFavoriteRecipes(favoriteRecipesEntity)
        changeMenuItemColor(item, yellowColor)
        createSnackBar("Recipe saved.")
        recipesSaved = true
    }

    private fun deleteFavoriteRecipes(item: MenuItem) {
        val favoriteRecipesEntity = FavoriteRecipesEntity(recipeSavedId, args.result)
        mViewModel.deleteFavoriteRecipe(favoriteRecipesEntity)
        changeMenuItemColor(item, whiteColor)
        createSnackBar("Recipe Removed.")
        recipesSaved = false
    }

    private fun createSnackBar(message: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon?.setTint(ContextCompat.getColor(this, color))
    }
}