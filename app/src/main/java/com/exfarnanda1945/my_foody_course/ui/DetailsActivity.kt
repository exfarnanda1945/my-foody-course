package com.exfarnanda1945.my_foody_course.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.navArgs
import com.exfarnanda1945.my_foody_course.R
import com.exfarnanda1945.my_foody_course.adapter.PagerAdapter
import com.exfarnanda1945.my_foody_course.databinding.ActivityDetailsBinding
import com.exfarnanda1945.my_foody_course.ui.fragments.ingredients.IngredientsFragment
import com.exfarnanda1945.my_foody_course.ui.fragments.instructions.InstructionsFragment
import com.exfarnanda1945.my_foody_course.ui.fragments.overview.OverviewFragment
import com.exfarnanda1945.my_foody_course.util.Constants.RECIPE_BUNDLE_KEY
import com.google.android.material.tabs.TabLayoutMediator

class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var adapterViewPager: PagerAdapter

    private val args by navArgs<DetailsActivityArgs>()

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
            adapter = adapterViewPager
            currentItem = 0
        }

        TabLayoutMediator(binding.tabBarLayout, binding.viewPager) { tab, position ->
            tab.text = adapterViewPager.getTitleTab(position)
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}