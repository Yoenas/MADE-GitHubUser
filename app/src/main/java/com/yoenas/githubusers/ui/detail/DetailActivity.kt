package com.yoenas.githubusers.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.FollowPagerAdapter
import com.yoenas.githubusers.core.data.Resource
import com.yoenas.githubusers.core.domain.model.User
import com.yoenas.githubusers.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    private var _dataUser: User? = null
    private val dataUser get() = _dataUser as User

    private var saveDataUser = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.txt_title_detail)
        }

        val username = intent.getStringExtra(EXTRA_DATA_USERNAME)
        saveDataUser.putString(EXTRA_DATA_USERNAME, username)

        username?.let { query ->
            detailViewModel.getUserDetails(query).observe(this) {
                _dataUser = it.data
                when (it) {
                    is Resource.Loading -> showLoading(true)
                    is Resource.Success -> {
                        initView(it.data)
                        showLoading(false)
                    }
                    is Resource.Error -> {
                        Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                        showLoading(false)
                    }
                }
            }
        }
    }

    private fun initView(data: User?) {
        val tabList = arrayOf(getString(R.string.txt_followers), getString(R.string.txt_following))

        binding.apply {
            data?.let { dataUser ->
                Glide.with(applicationContext).load(dataUser.avatarUrl)
                    .error(R.drawable.ic_broken_image_dark)
                    .into(imgAvatarDetail)
                tvNameDetail.text = dataUser.name
                tvUsernameDetail.text = dataUser.login
                tvCompanyDetail.text = dataUser.company
                tvLocationDetail.text = dataUser.location
                tvRepositaryDetail.text = dataUser.publicRepos.toString()
                tvFollowersDetail.text = dataUser.followers.toString()
                tvFollowingDetail.text = dataUser.following.toString()

                detailViewModel.showUserIsFavorite(dataUser)
                fabFavorite.setOnClickListener {
                    detailViewModel.checkFavoriteUser(dataUser)
                }
            }

            detailViewModel.isFavorite.observe(this@DetailActivity) { isFav ->
                if (isFav) {
                    fabFavorite.setImageResource(R.drawable.ic_favorite_full)
                } else {
                    fabFavorite.setImageResource(R.drawable.ic_favorite_border)
                }
            }

            vpFollow.adapter = FollowPagerAdapter(this@DetailActivity, saveDataUser)
            TabLayoutMediator(tabs, vpFollow) { tab, position ->
                tab.text = tabList[position]
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu_share -> shareUserProfile()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareUserProfile() {
        dataUser.apply {
            val textValue = "This is $name's GitHub Profile\n" +
                    "Username: $login\n" +
                    "Email: $email\n" +
                    "Company: $company\n" +
                    "Location: $location\n" +
                    "Visit link $htmlUrl"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textValue)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.txt_share_to))
            startActivity(shareIntent)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                imgAvatarDetail.visibility = View.GONE
                imgCompany.visibility = View.GONE
                imgLocation.visibility = View.GONE
                imgRepo.visibility = View.GONE
                tvRepo.visibility = View.GONE
                tvFollowers.visibility = View.GONE
                tvFollowing.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                imgAvatarDetail.visibility = View.VISIBLE
                imgCompany.visibility = View.VISIBLE
                imgLocation.visibility = View.VISIBLE
                imgRepo.visibility = View.VISIBLE
                tvRepo.visibility = View.VISIBLE
                tvFollowers.visibility = View.VISIBLE
                tvFollowing.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_DATA_USERNAME = "username"
    }
}