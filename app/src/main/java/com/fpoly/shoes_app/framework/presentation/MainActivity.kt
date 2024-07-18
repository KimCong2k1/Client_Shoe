package com.fpoly.shoes_app.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding? get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navBottom)
            .setupWithNavController(navController)

//        // block item in navBottom
//        binding?.navBottom?.run {
//            menu.findItem(selectedItemId).isEnabled = false
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    internal fun showBottomNavigation(enable: Boolean) {
        binding?.navBottom?.isVisible = enable
    }
}