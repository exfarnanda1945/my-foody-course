package com.exfarnanda1945.my_foody_course.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

// pas the value from details activity to fragment inside tab
// https://stackoverflow.com/questions/56778106/fragmentpageradapter-deprecated
class PagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val resultBundle: Bundle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val titleList: MutableList<String> = ArrayList()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }

    fun getTitleTab(position: Int): String {
        return titleList[position]
    }

    override fun createFragment(position: Int): Fragment {
        fragmentList[position].arguments = resultBundle
        return fragmentList[position]
    }
}