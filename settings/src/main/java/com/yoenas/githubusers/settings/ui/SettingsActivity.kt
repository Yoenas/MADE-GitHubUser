package com.yoenas.githubusers.settings.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.yoenas.githubusers.di.SettingsModuleDependencies
import com.yoenas.githubusers.settings.DaggerSettingsComponent
import com.yoenas.githubusers.settings.ViewModelFactory
import com.yoenas.githubusers.settings.databinding.ActivitySettingsBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val settingsViewModel: SettingsViewModel by viewModels {
        factory
    }

    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding as ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerSettingsComponent.builder()
            .context(this)
            .create(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    SettingsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)

        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsViewModel.getThemeSettings().observe(this) {
            if (!it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDarkMode.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDarkMode.isChecked = false
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked: Boolean ->
            settingsViewModel.saveThemeSetting(!isChecked)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}