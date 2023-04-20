package com.app.e_commerce_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.e_commerce_app.databinding.ActivityMainBinding
import com.app.e_commerce_app.databinding.FragmentLoginBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNav()
    }

    private fun setupNav() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_host_fragment) as NavHostFragment
        controller = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(controller)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> hideBottomNav()
                R.id.signupFragment -> hideBottomNav()
                else -> showBottomNav()
            }
        }
    }

    private fun showBottomNav() {
        binding.navigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navigationView.visibility = View.GONE
    }
}