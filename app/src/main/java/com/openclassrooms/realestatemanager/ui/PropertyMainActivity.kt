package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ActivityMainPropertylBinding
import com.openclassrooms.realestatemanager.models.Property


class PropertyMainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainPropertylBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()

        binding = ActivityMainPropertylBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)


        val fragment: PropertyListFragment = navHostFragment.childFragmentManager.fragments[0] as PropertyListFragment

        Log.v("fragment OnCreate", fragment.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.add_property_button -> {
                val createPropertyIntent = Intent(this, EditPropertyDetailActivity::class.java)
                createPropertyIntent.putExtra("isCreate", true)
                startActivity(createPropertyIntent)
                true
            }
            R.id.show_search_button -> {
                val searchPropertyIntent = Intent(this, SearchPropertyActivity::class.java)
                searchPropertyActivityResultLauncher.launch(searchPropertyIntent)
                true
            }
            R.id.show_map_button -> {
                val action = PropertyListFragmentDirections.showMapFragment()
                binding.navHostFragmentItemDetail.findNavController().navigate(action)
                true
            }
            R.id.loan_simulator_button -> {
                val intent = Intent(this, SimulatorActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var searchPropertyActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val value = it.data?.getParcelableArrayListExtra<Property>("ActivityResult")

            val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment

            val fragment: PropertyListFragment = navHostFragment.childFragmentManager.fragments[0] as PropertyListFragment

            if (value != null) {
                fragment.updateList(value)
            }
        }
    }

    private fun createNotificationChannel() {
        val name = getString(R.string.notification_channel_name)
        val descriptionText = getString(R.string.notification_channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}