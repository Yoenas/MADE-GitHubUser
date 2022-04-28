package com.yoenas.githubusers.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.ui.UserAdapter
import com.yoenas.githubusers.databinding.FragmentFollowingBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private val followingViewModel: FollowingViewModel by viewModels()

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        val username = arguments?.getString(DetailActivity.EXTRA_DATA_USERNAME).toString()

        val userAdapter = UserAdapter()
        followingViewModel.getResultFollowing(username).observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> showLoading(true)
                is Resource.Success -> {
                    userAdapter.setData(it.data)
                    showLoading(false)
                }
                is Resource.Error -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }

        binding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = userAdapter
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                rvFollowing.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvFollowing.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}