package com.yoenas.githubusers.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.ui.UserAdapter
import com.yoenas.githubusers.databinding.FragmentFollowersBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.navigation.KeyName
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private val followersViewModel: FollowersViewModel by viewModels()

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding as FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        val username = arguments?.getString(KeyName.DATA_USERNAME).toString()

        val userAdapter = UserAdapter()
        followersViewModel.getResultFollowers(username).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    userAdapter.setData(it.data)
                    showLoading(false)
                }
                is Resource.Error -> {
                    showLoading(false)
                }
            }
        }

        binding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = userAdapter
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                rvFollowers.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvFollowers.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}