package com.ihsan.sona3

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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.ihsan.sona3.data.db.AppDatabase
import com.ihsan.sona3.data.network.ApiSettings
import com.ihsan.sona3.utils.SharedKeyEnum
import com.ihsan.sona3.utils.Sona3Preferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var fab: FloatingActionButton
    lateinit var mainAppbar: AppBarLayout
    lateinit var drawerLayout: DrawerLayout
    lateinit var toolbar: Toolbar
    lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: 6/29/2021 Uncomment After solving layout issue
        //supportRTL()

        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mainAppbar = findViewById(R.id.mainAppbar)
        fab = findViewById(R.id.fab)


        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }


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
        fab.visibility = visibility
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
            R.id.nav_home -> navController.navigate(R.id.nav_home)
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

    private fun userLogOut() {
        ApiSettings.apiInstance.logOut(
            "Token ${Sona3Preferences().getString(SharedKeyEnum.TOKEN.toString())!!}"
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    removeUserLocalData()
                    Timber.i("Logout, Completed")
                },
                { error -> Timber.i(error) }
            )
    }

    private fun removeUserLocalData() {
        Sona3Preferences().removeValue(SharedKeyEnum.TOKEN.toString())
        Sona3Preferences().setBoolean(SharedKeyEnum.FIRST_LOGIN.toString(), true)

        AppDatabase.invoke(this).getUserDao().delete()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { Timber.i("Data Deleted") },
                { Timber.i("Error: $it") }
            )
    }

    private fun dialog() {
        val builderX = AlertDialog.Builder(this)
            .setMessage("التاكيد علي تسجيل الخروج")
            .setPositiveButton("نعم") { _, _ ->
                userLogOut()
            }
            .setNegativeButton("لا") { _, _ ->

            }

        val dialog = builderX.create()
        dialog.show()
    }
}
