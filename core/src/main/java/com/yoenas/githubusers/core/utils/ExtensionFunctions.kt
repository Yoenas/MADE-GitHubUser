package com.yoenas.githubusers.core.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar

object ExtensionFunctions {

    fun MaterialToolbar.setupActionBar(fragment: Fragment) {

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        this.apply {
            setupWithNavController(navController, appBarConfiguration)
            (fragment.requireActivity() as AppCompatActivity).setSupportActionBar(this)
            (fragment.requireActivity() as AppCompatActivity).setupActionBarWithNavController(
                navController,
                appBarConfiguration
            )
        }
    }
}