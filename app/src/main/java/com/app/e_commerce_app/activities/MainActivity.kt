package com.app.e_commerce_app.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseActivity
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.common.listHideBottomNavigationView
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNav()
        setupRemember()
    }


    private fun setupRemember() {
        val sharePreference = AppSharePreference(this)
        val tokenRepository = TokenRepository(sharePreference)
        val isRemember = tokenRepository.getRemember()
        if (isRemember == null || isRemember == false) {
            tokenRepository.removeToken()
        } else {
            controller.navigate(R.id.homeFragment)
        }
    }

    private fun setupNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_host_fragment) as NavHostFragment
        controller = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(controller)
    }

    private fun showBottomNav() {
        binding.navigationView.visibility = View.VISIBLE
    }

    private fun hideBottomNav() {
        binding.navigationView.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(controller) || super.onOptionsItemSelected(item)
    }

    override fun showLoading(isShow: Boolean) {
        binding.loadingLayout.bringToFront()
        if(isShow) {
            Log.d("HIDE", "")
            hideBottomNav()
            binding.loadingLayout.visibility = View.VISIBLE
        }
        else {
            binding.loadingLayout.visibility = View.GONE
            controller.addOnDestinationChangedListener { _, destination, _ ->
                if (listHideBottomNavigationView.indexOf(destination.id) >= 0)
                    hideBottomNav()
                else
                    showBottomNav()
            }
        }
    }
}