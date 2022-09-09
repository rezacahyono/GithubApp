package com.rchyn.githubapp.ui.fragments.detail

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayoutMediator
import com.rchyn.githubapp.R
import com.rchyn.githubapp.databinding.DialogStatusStateBinding
import com.rchyn.githubapp.databinding.FragmentDetailUserBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.ViewModelFactory
import com.rchyn.githubapp.ui.fragments.follower.FollowersSectionPageAdapter
import com.rchyn.githubapp.ui.fragments.follower.FollowersViewModel
import com.rchyn.githubapp.util.*


class DetailUserFragment : Fragment() {
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding as FragmentDetailUserBinding

    private var _bindingDialog: DialogStatusStateBinding? = null
    private val bindingDialog get() = _bindingDialog as DialogStatusStateBinding


    private val args: DetailUserFragmentArgs by navArgs()

    private val detailViewModel: DetailUserViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private val followersViewModel: FollowersViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext())
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
        detailViewModel.setUsername(user.username)
        followersViewModel.setUsername(user.username)

        detailViewModel.userDetail.observe(viewLifecycleOwner) { result ->
            when {
                result.isLoading -> binding.loadingBar.show()
                result.user != null -> {
                    binding.loadingBar.hide()
                    setupDisplayDetail(result.user)
                }
                result.isError != 0 -> setupDialogState(result.isError)
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

        setupMenu()
    }

    private fun setupDisplayDetail(user: User) {
        binding.apply {
            ivAvatar.load(user.avatar) {
                crossfade(true)
            }
            tvName.text = user.name
            tvUsername.text = user.username
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

            val icon: Drawable? = if (user.isFavorite) {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_favorite_small)
            } else {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_outline_favorite_small)
            }

            btnFavorite.apply {
                setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
                setOnClickListener {
                    detailViewModel.setFavorite(user)
                }
            }
        }
    }

    private fun setupMenu() {
        binding.toolbarMain.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.nav_settings -> {
                    findNavController().navigate(R.id.nav_settings)
                    true
                }
                R.id.nav_favorite -> {
                    findNavController().navigate(R.id.nav_favorite)
                    true
                }
                else -> false
            }
        }
    }

    private fun setupDialogState(@StringRes text: Int) {
        var dialog: MaterialAlertDialogBuilder? = null
        _bindingDialog = DialogStatusStateBinding.inflate(LayoutInflater.from(requireContext()))
        bindingDialog.tvPlaceholder.text = getString(text)
        if (dialog == null) {
            dialog = MaterialAlertDialogBuilder(requireContext())
                .setView(bindingDialog.root)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.back)) { it, _ ->
                    findNavController().navigateUp()
                    it.dismiss()
                }
            dialog.show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}