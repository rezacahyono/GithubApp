package com.rchyn.githubapp.ui.fragment.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.rchyn.githubapp.GithubApplication
import com.rchyn.githubapp.R
import com.rchyn.githubapp.databinding.FragmentDetailUserBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.ViewModelFactory
import com.rchyn.githubapp.ui.fragment.follower.FollowersSectionPageAdapter
import com.rchyn.githubapp.ui.fragment.follower.FollowersViewModel
import com.rchyn.githubapp.util.*


class DetailUserFragment : Fragment() {
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!

    private val args: DetailUserFragmentArgs by navArgs()

    private val detailViewModel: DetailUserViewModel by viewModels {
        ViewModelFactory(
            (activity?.application as GithubApplication).repository
        )
    }
    private val followersViewModel: FollowersViewModel by activityViewModels {
        ViewModelFactory(
            (activity?.application as GithubApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        binding.toolbarMain.apply {
            title = getString(R.string.detail)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMain.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        val tabTitle = resources.getStringArray(R.array.tab_title_followers)
        val viewPager = binding.viewPagerFollowers
        viewPager.adapter = FollowersSectionPageAdapter(this)
        val tab = binding.tabFollowers
        TabLayoutMediator(tab, viewPager) { tabs, position ->
            tabs.text = tabTitle[position]
        }.attach()

        val user = args.user
        detailViewModel.setUsername(user.login)
        followersViewModel.setUsername(user.login)

        detailViewModel.userDetail.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Resource.Success -> {
                    state.data?.let {
                        setupDisplayDetail(it)
                    }
                    binding.loadingBar.hide()
                }
                is Resource.Loading -> {
                    binding.loadingBar.show()
                }
                is Resource.Error -> {
                    binding.loadingBar.hide()
                }
            }
        }

        followersViewModel.userFollowers.observe(viewLifecycleOwner) { state ->
            if (state is Resource.Loading) {
                binding.loadingBar.show()
            } else {
                binding.loadingBar.hide()
            }
        }

        followersViewModel.userFollowing.observe(viewLifecycleOwner) { state ->
            if (state is Resource.Loading) {
                binding.loadingBar.show()
            } else {
                binding.loadingBar.hide()
            }
        }
    }

    private fun setupDisplayDetail(user: User) {
        binding.apply {
            ivAvatar.load(user.avatar)
            tvName.text = user.name
            tvUsername.text = user.login
            tvCompany.text = user.company.ifBlank {
                tvCompany.hide()
                ""
            }
            tvLocation.text = user.location.ifBlank {
                tvLocation.hide()
                ""
            }
            tvEmail.text = user.email.ifBlank {
                tvEmail.hide()
                ""
            }
            tvBlog.text = user.blog.ifBlank {
                tvBlog.hide()
                ""
            }
            tvTwitter.text = user.twitter.addAtSign().ifBlank {
                tvTwitter.hide()
                ""
            }
            tvRepository.text = getString(R.string.repository, user.repository)
            tvFollowers.text = user.followers.prettyNumber()
            tvFollowing.text = getString(R.string.followings, user.following.prettyNumber())

            if (user.blog.isNotBlank()) {
                val intentBlogPage = Intent(Intent.ACTION_VIEW, Uri.parse(user.blog.addHttps()))
                tvBlog.setOnClickListener {
                    startActivity(intentBlogPage)
                }
            }

            val intentGithubPage = Intent(Intent.ACTION_VIEW, Uri.parse(user.url))
            btnSeeLink.setOnClickListener {
                startActivity(intentGithubPage)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}