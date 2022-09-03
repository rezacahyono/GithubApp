package com.rchyn.githubapp.ui.fragment.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rchyn.githubapp.GithubApplication
import com.rchyn.githubapp.R
import com.rchyn.githubapp.adapter.ListUserAdapter
import com.rchyn.githubapp.databinding.FragmentFollowersBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.ViewModelFactory
import com.rchyn.githubapp.util.Resource
import com.rchyn.githubapp.util.hide
import com.rchyn.githubapp.util.show


class FollowersFragment : Fragment() {
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private var section: String? = null

    private lateinit var listUserAdapter: ListUserAdapter

    private val followersViewModel: FollowersViewModel by activityViewModels {
        ViewModelFactory(
            (activity?.application as GithubApplication).repository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            section = it.getString(SECTION_EXTRAS)
        }
        listUserAdapter = ListUserAdapter { user ->
            navigateToDetail(user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUser.apply {
            layoutManager = linearLayoutManager
            adapter = listUserAdapter
        }
        section?.let {
            when (it) {
                FOLLOWERS -> setupRecyclerUserFollowers()
                FOLLOWING -> setupRecyclerUserFollowing()
            }
        }
    }

    private fun setupRecyclerUserFollowers() {
        followersViewModel.userFollowers.observe(viewLifecycleOwner) { result ->
            if (result is Resource.Success) {
                listUserAdapter.submitList(result.data)
                binding.recyclerUser.show()
            } else {
                binding.recyclerUser.hide()
            }
        }
    }

    private fun setupRecyclerUserFollowing() {
        followersViewModel.userFollowing.observe(viewLifecycleOwner) { result ->
            if (result is Resource.Success) {
                listUserAdapter.submitList(result.data)
                binding.recyclerUser.show()
            } else {
                binding.recyclerUser.hide()
            }
        }
    }

    private fun navigateToDetail(user: User) {
        val bundle = bundleOf("user" to user)
        findNavController().navigate(R.id.action_nav_followers_to_nav_detail, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        section = null
    }

    companion object {

        const val FOLLOWERS = "followers"
        const val FOLLOWING = "following"
        const val SECTION_EXTRAS = "section_extras"
    }
}