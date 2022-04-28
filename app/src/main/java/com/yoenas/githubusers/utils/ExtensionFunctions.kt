package com.yoenas.githubusers.utils

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.yoenas.githubusers.R
import com.yoenas.githubusers.ui.MainActivity

object ExtensionFunctions {
    fun MaterialToolbar.setupActionBar(fragment: Fragment, destinationId: Int?) {

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        this.apply {
            setupWithNavController(navController, appBarConfiguration)
            (fragment.requireActivity() as MainActivity).setSupportActionBar(this)
            (fragment.requireActivity() as MainActivity).setupActionBarWithNavController(
                navController,
                appBarConfiguration
            )
            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    destinationId -> {
                        this.setNavigationIcon(R.drawable.ic_left)
                    }
                }
            }
        }
    }
}