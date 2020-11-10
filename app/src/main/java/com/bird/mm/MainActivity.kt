package com.bird.mm

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bird.mm.util.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val intent = intent
            val pp = intent.data
            val ref = this.referrer
            val host = ref?.host
            val authority = ref?.authority
            val sechme = ref?.scheme
            val path = ref?.path
            val query = ref?.query

            callingActivity?.className

            val from = "host is $host \n" +
                    "authority is $authority \n" +
                    "sechme is $sechme \n" +
                    "path is $path \n" +
                    "query is $query \n" +
                    "query is ${pp?.path} \n"
            findViewById<TextView>(R.id.tv_from).text = from
            last()
        } else {
        }

    }

    fun last(){
        val man = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val list = man.getRecentTasks(1, 0)
        man.appTasks.forEach {
            Log.e("MainActivity", "appTasks last launch : ${it.taskInfo} ")
        }
        list.forEach {
            Log.e("MainActivity", "last launch : ${it.toString()}")
        }
    }

    fun lastActivity(){
        val am =
            this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val recentTasks =
            am.getRecentTasks(10000, ActivityManager.RECENT_WITH_EXCLUDED)
        recentTasks.forEach {
            Log.e("MainActivity", "last launch : ${it.toString()}")
        }
    }

    override fun onStart() {
        super.onStart()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(
//            R.id.navigation_scheme, R.id.navigation_home, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        val fragmentNavigator = FixFragmentNavigator(this,supportFragmentManager,R.id.nav_host_fragment)
//        navController.navigatorProvider.addNavigator(fragmentNavigator)

        val navIds = listOf(R.navigation.home, R.navigation.list, R.navigation.mobile_navigation)
//        val navIds = listOf(R.navigation.home)
//        val navIds = listOf(R.navigation.mobile_navigation)
        val controller = navView.setupWithNavController(
            navGraphIds = navIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller.observe(this, Observer {
            setupActionBarWithNavController(it)
        })

        currentNavController = controller

//        navView.visibility = View.VISIBLE
        navView.visibility = View.GONE

//        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//        val navController = findNavController(R.id.nav_host_fragment)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_scheme, R.id.navigation_scheme, R.id.navigation_scheme
//            )
//        )
////        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            when (destination.id) {
//                R.id.navigation_home,
//                R.id.navigation_bg,
//                R.id.navigation_scheme,
//                R.id.navigation_notifications -> navView.visibility = View.VISIBLE
//                else -> navView.visibility = View.GONE
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}
