package com.rchyn.githubapp.ui.fragments.follower

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowersSectionPageAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return FollowersFragment().apply {
            val section: String = when (position) {
                0 -> FollowersFragment.FOLLOWERS
                1 -> FollowersFragment.FOLLOWING
                else -> {
                    ""
                }
            }
            arguments = bundleOf(
                FollowersFragment.SECTION_EXTRAS to section
            )
        }

    }
}