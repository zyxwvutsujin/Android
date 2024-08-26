package com.example.app08_menu.customMenu

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyFragmentPagerAdapter(activity: FragmentActivity)
    : FragmentStateAdapter(activity) {
    val fragment: List<Fragment>
    init {
        fragment = listOf(FragmentOne(), FragmentTwo(), FragmentThree())
    }
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}