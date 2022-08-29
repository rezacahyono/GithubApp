package com.rchyn.githubapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.rchyn.githubapp.R
import com.rchyn.githubapp.databinding.ActivityMainBinding
import com.rchyn.githubapp.ui.fragment.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)

        val toolbar = binding.toolbarMain as Toolbar
        setSupportActionBar(toolbar)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)
        if (fragment !is HomeFragment) {
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.fragment_container_view,
                    homeFragment,
                    HomeFragment::class.java.simpleName
                )
                .commit()
        }
    }
}