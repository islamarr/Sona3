/*
 * Last modified 7/12/21, 2:21 PM
 */

package com.ihsan.sona3.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.navigation.NavigationView
import com.ihsan.sona3.R
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.utils.toast
import timber.log.Timber


class MainActivity : AppCompatActivity(), MainContract.View,
    NavigationView.OnNavigationItemSelectedListener {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var mainAppbar: AppBarLayout
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navController: NavController

    lateinit var db: AppDatabase
    lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 6/29/2021 Uncomment After solving layout issue
        //supportRTL()

        setContentView(R.layout.activity_main)

        db = AppDatabase.invoke(this)
        mainPresenter = MainPresenter(db, this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mainAppbar = findViewById(R.id.mainAppbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_profile, R.id.nav_my_form, R.id.nav_logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val onActionExpandListener = object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                return true
            }


        }
        menu.findItem(R.id.action_search).setOnActionExpandListener(onActionExpandListener)

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun setHomeItemsVisibility(visibility: Int, mode: Int) {
        mainAppbar.visibility = visibility
        drawerLayout.setDrawerLockMode(mode)
    }

    private fun supportRTL() {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_logout -> {
                Timber.i("Logout Here")
                dialog()
            }
            R.id.nav_home -> {
                navController.navigate(R.id.nav_home)
                navController.popBackStack(R.id.mobile_navigation, true)
            }
            R.id.nav_profile -> navController.navigate(R.id.nav_profile)
            R.id.nav_my_form -> navController.navigate(R.id.nav_my_form)
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
        //navController.navigateUp(appBarConfiguration)
//        if (item.itemId == R.id.nav_logout) {
//            Timber.i("Logout Here")
//            dialog()
//        }
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun dialog() {
        val builderX = AlertDialog.Builder(this)
            .setMessage("?????????????? ?????? ?????????? ????????????")
            .setPositiveButton("??????") { _, _ ->
                mainPresenter.userLogOut()
            }
            .setNegativeButton("????") { _, _ ->

            }

        val dialog = builderX.create()
        dialog.show()
    }

    override fun onLogOutSuccess() {
        this.toast("???? ?????????? ???????????? ??????????")
        mainPresenter.userDeleteLocal()
        navController.popBackStack(R.id.nav_home, true)
        navController.navigate(R.id.splashFragment)
    }

    override fun onError(error: String?) {
        Timber.i(error)
        this.toast(error!!)
    }
}
