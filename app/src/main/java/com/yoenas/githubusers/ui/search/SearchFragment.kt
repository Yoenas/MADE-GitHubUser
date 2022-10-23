package com.yoenas.githubusers.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.R
import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.ui.UserAdapter
import com.yoenas.githubusers.core.utils.ExtensionFunctions.setupActionBar
import com.yoenas.githubusers.core.utils.OnItemClickCallback
import com.yoenas.githubusers.databinding.FragmentSearchBinding
import com.yoenas.navigation.ActivityName
import com.yoenas.navigation.KeyName
import com.yoenas.navigation.intentTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding as FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.toolbarSearch.setupActionBar(this)

        val userAdapter = UserAdapter()

        searchViewModel.getSearchUser().observe(viewLifecycleOwner) { list ->
                when (list) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        userAdapter.setData(list.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "Error ${list.message}", Toast.LENGTH_SHORT).show()
                        Log.e("SearchFragment", "Error retrofit2 :\n${list.message}")
                        showLoading(false)
                        binding.tvDialogInformation.visibility = View.VISIBLE
                        binding.imgDialogInformation.visibility = View.VISIBLE
                        binding.rvUser.visibility = View.GONE
                    }
                }
            }

        binding.rvUser.apply {
            layoutManager = GridLayoutManager(context, 2)
            userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(userResponse: User) {
                    val detail = ActivityName.DETAIL
                    context.intentTo(detail, KeyName.DATA_USERNAME, userResponse.login)
                }
            })
            adapter = userAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        searchView.queryHint = resources.getString(R.string.txt_type_the_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUserByQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val uri = Uri.parse("githubusers://settings")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
            R.id.action_favorite ->
                findNavController().navigate(R.id.action_searchFragment_to_favoriteFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUserByQuery(query: String?) {
        if (query != null) {
            query.let { searchViewModel.setSearch(it) }
            showLoading(true, query)
        } else {
            showLoading(false)
        }
    }

    private fun showLoading(loading: Boolean, username: String? = null) {
        binding.apply {
            if (loading) {
                rvUser.visibility = View.GONE
                tvDialogInformation.visibility = View.GONE
                imgDialogInformation.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvSearchProgress.visibility = View.VISIBLE
                tvSearchQuery.text = username
                tvSearchQuery.visibility = View.VISIBLE
            } else {
                tvDialogInformation.visibility = View.GONE
                imgDialogInformation.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvSearchProgress.visibility = View.GONE
                tvSearchQuery.visibility = View.GONE
                rvUser.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}