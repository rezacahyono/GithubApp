package com.rchyn.githubapp.ui.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rchyn.githubapp.GithubApplication
import com.rchyn.githubapp.R
import com.rchyn.githubapp.adapter.ListUserAdapter
import com.rchyn.githubapp.databinding.FragmentHomeBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.ViewModelFactory
import com.rchyn.githubapp.util.Resource
import com.rchyn.githubapp.util.hide
import com.rchyn.githubapp.util.show

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var listUserAdapter: ListUserAdapter
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory(
            (activity?.application as GithubApplication).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUser.layoutManager = linearLayoutManager
        homeViewModel.listUser.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    setupRetrieveRecyclerUser(result.data)
                    binding.apply {
                        recyclerUser.show()
                        loadingBar.hide()
                        ivPlaceholder.hide()
                        tvPlaceholder.hide()
                    }
                    Log.d("TAG", "onViewCreated: $result")
                }
                is Resource.Loading -> {
                    binding.apply {
                        recyclerUser.hide()
                        loadingBar.show()
                        ivPlaceholder.hide()
                        tvPlaceholder.hide()
                    }
                    Log.d("TAG", "onViewCreated: $result")
                }
                is Resource.Error -> {
                    binding.apply {
                        recyclerUser.hide()
                        loadingBar.hide()
                        ivPlaceholder.apply {
                            setImageResource(R.drawable.ic_placeholder_error)
                            show()
                        }
                        tvPlaceholder.apply {
                            text = result.message ?: ""
                            show()
                        }
                    }
                    Log.d("TAG", "onViewCreated: $result")
                }
            }
        }
        setupSearchView()
    }

    private fun setupRetrieveRecyclerUser(users: List<User>?) {
        listUserAdapter = ListUserAdapter { user ->
            navigateToDetail(user)
        }.apply {
            submitList(users)
        }
        binding.recyclerUser.adapter = listUserAdapter
    }

    private fun setupSearchView() {
        val searchView = binding.layoutToolbar.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    homeViewModel.setSearchQuery(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun navigateToDetail(user: User) {
        val action = HomeFragmentDirections.actionNavHomeToNavDetail(user)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}