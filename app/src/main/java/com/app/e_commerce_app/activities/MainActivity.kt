package com.app.e_commerce_app.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.app.e_commerce_app.R
import com.app.e_commerce_app.base.BaseActivity
import com.app.e_commerce_app.base.BaseFragment
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.data.repository.TokenRepository
import com.app.e_commerce_app.databinding.ActivityMainBinding
import com.app.e_commerce_app.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        setupNav()
        binding.navigationView.setOnItemSelectedListener {
            controller.navigate(it.itemId)
            true
        }

        binding.navigationView.setOnItemReselectedListener {
            false
        }
    }

    private fun setupNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_host_fragment) as NavHostFragment
        controller = navHostFragment.findNavController()
        binding.navigationView.setupWithNavController(controller)
    }

    override fun showLoading(isShow: Boolean) {
        binding.loadingLayout.bringToFront()
        if (isShow) {
            Utils.hideSoftKeyboard(binding.root, this)
//            binding.navigationView.visibility = View.GONE
            binding.loadingLayout.visibility = View.VISIBLE
        } else {
//            val navHostFragment =
//                supportFragmentManager.findFragmentById(R.id.my_host_fragment) as NavHostFragment
//            val frag =
//                navHostFragment.childFragmentManager.primaryNavigationFragment as BaseFragment<*>
//            if (!frag.isHideBottomNavigationView)
//                binding.navigationView.visibility = View.VISIBLE
            binding.loadingLayout.visibility = View.GONE
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("CHECKMAIN", "BEFORE")
        val tokenRepository = TokenRepository(AppSharePreference(applicationContext))
        if (tokenRepository.getRemember() == false)
            tokenRepository.removeToken()
        Log.d("CHECKMAIN", "AFTER")
    }
}