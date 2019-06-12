package com.example.proyecto.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.example.proyecto.R
import fragments.*
import com.example.proyecto.RequestCode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var currentLoadedFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setToolbar()
        loadUserDataOrSendToLoginActivity()

    }

    private fun setToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
        }
        setupNavViewListener()
    }

    private fun setupNavViewListener() {
        navView.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawerLayout.closeDrawers()

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            val transaction = supportFragmentManager.beginTransaction()

            when (menuItem.itemId) {
                R.id.home -> {
                    val profileFragment = supportFragmentManager.findFragmentByTag("profileFrag")
                    if (profileFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, profileFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Profile(), "profileFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_home_title)
                }

                R.id.matches -> {
                    val matchesFragment = supportFragmentManager.findFragmentByTag("closeMatchFrag")
                    if (matchesFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, matchesFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Close_matches(), "closeMatchFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_close_matches_title)
                }

                R.id.request -> {
                    val requestsFragment = supportFragmentManager.findFragmentByTag("requestsFrag")
                    if (requestsFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, requestsFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Join_request(), "requestsFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_join_requests_title)
                }

                R.id.reserve -> {
                    val reservesFragment= supportFragmentManager.findFragmentByTag("reservesFrag")
                    if(reservesFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, reservesFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Reserve_flied(), "reservesFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_reserve_field_title)
                }

                R.id.payment -> {
                    val paymentsFragment = supportFragmentManager.findFragmentByTag("paymentsFrag")
                    if (paymentsFragment != null) {
                        transaction.replace(R.id.contentFrameLayout, paymentsFragment)
                    } else {
                        transaction.replace(R.id.contentFrameLayout, Payment_method(), "paymentsFrag")
                    }
                    supportActionBar!!.title = getString(R.string.action_bar_payment_methods_title)
                }

                R.id.signOut -> {
                    onSignOut()
                }
            }

            transaction.commit()
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun onSignOut(){
        
    }

    private fun loadUserDataOrSendToLoginActivity() {
            goToLoginActivity()

    }


    private fun goToLoginActivity() {
        // Clear current fragment to release memory.
        if (currentLoadedFragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.remove(currentLoadedFragment!!).commit()
        }
        // Initialize LogIn Activity
        startActivityForResult(
            Intent(this, LoginActivity::class.java),
            RequestCode.GO_TO_LOGIN_FROM_MAIN_ACTIVITY.value)
    }
}
