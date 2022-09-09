package com.rchyn.githubapp.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rchyn.githubapp.R
import com.rchyn.githubapp.adapter.ListUserAdapter
import com.rchyn.githubapp.databinding.FragmentFavoriteBinding
import com.rchyn.githubapp.model.User
import com.rchyn.githubapp.ui.ViewModelFactory
import com.rchyn.githubapp.util.hide
import com.rchyn.githubapp.util.show


class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    private lateinit var listUserAdapter: ListUserAdapter

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        binding.toolbarMain.apply {
            title = getString(R.string.favorite)
            navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarMain.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        listUserAdapter = ListUserAdapter { user ->
            navigateToDetail(user)
        }

        favoriteViewModel.listUser.observe(viewLifecycleOwner) { result ->
            when {
                result.isLoading -> {
                    binding.apply {
                        loadingBar.show()
                        recyclerUser.hide()
                        ivPlaceholder.hide()
                        tvPlaceholder.hide()
                    }
                }
                result.listUser != null -> {
                    binding.loadingBar.hide()
                    if (result.listUser.isNotEmpty()) {
                        listUserAdapter.submitList(result.listUser)
                        binding.apply {
                            recyclerUser.show()
                            ivPlaceholder.hide()
                            tvPlaceholder.hide()
                        }
                    } else {
                        binding.apply {
                            recyclerUser.hide()
                            loadingBar.hide()
                            ivPlaceholder.apply {
                                setImageResource(R.drawable.ic_placeholder_error)
                                show()
                            }
                            tvPlaceholder.apply {
                                text = getString(R.string.text_message_not_found)
                                show()
                            }
                        }
                    }
                }
                result.isError != 0 -> {
                    binding.apply {
                        recyclerUser.hide()
                        loadingBar.hide()
                        ivPlaceholder.apply {
                            setImageResource(R.drawable.ic_placeholder_error)
                            show()
                        }
                        tvPlaceholder.apply {
                            text = getString(result.isError)
                            show()
                        }
                    }
                }

            }
        }

        setupRecyclerUser()

        setupMenu()
    }

    private fun setupRecyclerUser() {
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUser.apply {
            layoutManager = linearLayoutManager
            adapter = listUserAdapter
        }
    }

    private fun setupMenu() {
        binding.toolbarMain.apply {
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nav_settings -> {
                        findNavController().navigate(R.id.nav_settings)
                        true
                    }
                    else -> false
                }
            }
            menu.findItem(R.id.nav_favorite).isVisible = false
        }
    }

    private fun navigateToDetail(user: User) {
        val action = FavoriteFragmentDirections.actionNavFavoriteToNavDetail(user)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}