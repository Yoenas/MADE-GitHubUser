package com.yoenas.githubusers.favorite.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.core.ui.UserAdapter
import com.yoenas.githubusers.core.utils.OnItemClickCallback
import com.yoenas.githubusers.di.FavoriteModuleDependencies
import com.yoenas.githubusers.favorite.DaggerFavoriteComponent
import com.yoenas.githubusers.favorite.R
import com.yoenas.githubusers.favorite.ViewModelFactory
import com.yoenas.githubusers.favorite.databinding.FragmentFavoriteBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.githubusers.utils.ExtensionFunctions.setupActionBar
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onAttach(context: Context) {
        DaggerFavoriteComponent.builder()
            .context(context)
            .create(
                EntryPointAccessors.fromApplication(
                    context,
                    FavoriteModuleDependencies::class.java
                )
            )
            .builder()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.toolbarFavorite.setupActionBar(this)

        favoriteViewModel.getFavoriteUsers().observe(viewLifecycleOwner) {
            binding.apply {
                if (it?.isEmpty() == false) {
                    tvDialogInformation.visibility = View.GONE
                    imgDialogInformation.visibility = View.GONE
                    rvFavorite.visibility = View.VISIBLE
                    setupRecyclerView(it)
                } else {
                    tvDialogInformation.visibility = View.VISIBLE
                    imgDialogInformation.visibility = View.VISIBLE
                    rvFavorite.visibility = View.GONE
                }
            }
        }
    }

    private fun setupRecyclerView(data: List<User>?) {
        with(binding.rvFavorite) {
            val favoriteUsersAdapter = UserAdapter()
            favoriteUsersAdapter.setData(data)
            layoutManager = GridLayoutManager(context, 2)
            favoriteUsersAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(userResponse: User) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA_USERNAME, userResponse.login)
                    startActivity(intent)
                }
            })
            adapter = favoriteUsersAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.favorite_menu, menu)

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
                searchUserByQuery(newText)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchUserByQuery(query: String?) {
        val searchQuery = "%$query%"
        favoriteViewModel.getSearchUserFromDB(searchQuery).observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                val uri = Uri.parse("githubusers://settings")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}