package com.fpoly.shoes_app.framework.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fpoly.shoes_app.R
import com.fpoly.shoes_app.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 1001
    private lateinit var audioManager: AudioManager
    private val PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,

    )
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding? get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            audioManager = this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            _binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding?.root)
            if (!hasPermissions(this, *PERMISSIONS)) ActivityCompat.requestPermissions(
                this,
                PERMISSIONS,
                PERMISSION_REQUEST_CODE
            )
            else setupBottomNavigation()
        } catch (e: IOException) {
            Log.e("catch App", e.toString())
        }

    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        return permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun setupBottomNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.navBottom).setupWithNavController(navController)
    }

    override fun onStop() {
        super.onStop()
        resetAudioSettings()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    private fun resetAudioSettings() {
        audioManager.adjustStreamVolume(
            AudioManager.STREAM_MUSIC,
            AudioManager.ADJUST_UNMUTE,
            0
        )
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                setupBottomNavigation()
            } else {
                Toast.makeText(
                    this, "Bạn cần cấp tất cả các quyền để ứng dụng hoạt động", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    internal fun showBottomNavigation(enable: Boolean) {
        binding?.navBottom?.isVisible = enable
    }
}