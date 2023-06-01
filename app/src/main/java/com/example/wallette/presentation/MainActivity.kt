package com.example.wallette.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.common.data.TokenManager
import com.example.common.di.findComponentDependencies
import com.example.wallette.R
import com.example.wallette.databinding.ActivityMainBinding
import com.example.wallette.di.main.MainComponent
import com.example.wallette.navigation.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var tokenManager: TokenManager

    private fun inject() {
        MainComponent
            .init(this, findComponentDependencies())
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        inject()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val controller = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment).navController
        hideNavBars(controller)

        binding.bottomNavigationView.setupWithNavController(controller)
        navigator.attachNavController(controller, this)

        if (checkIfAuthorized())
            navigator.openStatistics()
        else
            navigator.openLoginPage()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            //R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkIfAuthorized(): Boolean {
        return !tokenManager.getAuthorizationToken().isNullOrEmpty()
    }

    private fun hideNavBars(controller: NavController) {
        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment -> setNavBarsVisibility(View.GONE)
                R.id.registerFragment -> setNavBarsVisibility(View.GONE)
                else -> setNavBarsVisibility(View.VISIBLE)
            }
        }
    }

    private fun setNavBarsVisibility(visibility: Int) {
        binding.appBarLayout.visibility = visibility
        binding.bottomAppBar.visibility = visibility
        binding.bottomNavigationView.visibility = visibility
        binding.addFloatingButton.visibility = visibility
    }
}