package com.rchyn.githubapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.MaterialToolbar
import com.rchyn.githubapp.R
import com.rchyn.githubapp.adapter.ListUserAdapter
import com.rchyn.githubapp.data.DataSource
import com.rchyn.githubapp.databinding.FragmentHomeBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.activities.MainActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var act: MainActivity

    private lateinit var listUserAdapter: ListUserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        act = activity as MainActivity
        listUserAdapter = ListUserAdapter { user ->
            navigateToDetail(user)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val toolbar = act.findViewById<MaterialToolbar>(R.id.toolbar_main)
        toolbar.navigationIcon = null
        act.supportActionBar?.apply {
            title = getString(R.string.github)
            setLogo(R.drawable.ic_logo_github_small)
        }
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupRecyclerUser()
    }

    private fun setupRecyclerUser() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUser.apply {
            layoutManager = linearLayoutManager
            adapter = listUserAdapter
        }

        val dataSource = DataSource.getUser(act)
        listUserAdapter.submitList(dataSource.users)
    }

    private fun navigateToDetail(user: User) {
        val detailUserFragment = DetailUserFragment()
        detailUserFragment.arguments = Bundle().apply {
            putParcelable(DetailUserFragment.EXTRA_USER, user)
        }
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .apply {
                replace(
                    R.id.fragment_container_view,
                    detailUserFragment,
                    DetailUserFragment::class.java.simpleName
                )
                addToBackStack(null)
                commit()
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}