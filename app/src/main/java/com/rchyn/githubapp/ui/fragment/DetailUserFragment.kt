package com.rchyn.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.rchyn.githubapp.R
import com.rchyn.githubapp.databinding.FragmentDetailUserBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.activities.MainActivity
import com.rchyn.githubapp.util.prettyNumber


class DetailUserFragment : Fragment() {
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private var user: User? = null
    private lateinit var act: MainActivity
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
        arguments?.let {
            user = it.getParcelable<User>(EXTRA_USER) as User
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        toolbar = act.findViewById(R.id.toolbar_main)
        toolbar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        act.supportActionBar?.apply {
            title = getString(R.string.detail)
            setLogo(null)
        }
        _binding = FragmentDetailUserBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            act.onBackPressed()
        }

        user?.let { setupDisplayDetail(it) }
    }

    private fun setupDisplayDetail(user: User) {
        val imageRes = act.resources.getIdentifier(user.avatar, null, act.packageName)
        binding.apply {
            ivAvatar.setImageResource(imageRes)
            tvName.text = user.name
            tvUsername.text = user.username
            tvCompany.text = user.company
            tvLocation.text = user.location
            tvRepository.text = getString(R.string.repository, user.repository)
            tvFollowers.text = user.follower.prettyNumber()
            tvFollowing.text = getString(R.string.followings, user.following.prettyNumber())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_USER = "user"
    }

}