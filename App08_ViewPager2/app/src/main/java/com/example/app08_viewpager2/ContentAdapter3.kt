package com.example.app08_viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ContentAdapter3(val fragmentActivity: FragmentActivity)
    :FragmentStateAdapter(fragmentActivity) {
        var fragments = listOf<Fragment>(Tab1Fragment(), Tab2Fragment(), Tab3Fragment())
    override fun getItemCount(): Int {
         return  fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}