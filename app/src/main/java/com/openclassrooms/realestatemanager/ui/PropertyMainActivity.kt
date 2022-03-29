package com.openclassrooms.realestatemanager.ui

import android.app.Activity
import android.content.Intent
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


        binding = ActivityMainPropertylBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment
        val navController = navHostFragment.navController
        appBarConfiguration = AppBarConfiguration(navController.graph)


        val fragment: PropertyListFragment = navHostFragment.childFragmentManager.fragments[0] as PropertyListFragment

        Log.v("fragment OnCreate",fragment.toString())
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
                val createPropertyIntent = Intent(this,EditPropertyDetailActivity::class.java)
                createPropertyIntent.putExtra("isCreate",true)
                startActivity(createPropertyIntent)
                true
            }
            R.id.show_search_button -> {
                val searchPropertyIntent = Intent(this,SearchPropertyActivity::class.java)
                searchPropertyActivityResultLauncher.launch(searchPropertyIntent)
                true
            }
            R.id.show_map_button -> {
                val action = PropertyListFragmentDirections.showMapFragment()
                binding.navHostFragmentItemDetail.findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private var searchPropertyActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val value = it.data?.getParcelableArrayListExtra<Property>("ActivityResult")

            Log.v("List Size Sortie",value?.size.toString())

            val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_item_detail) as NavHostFragment

            val fragment: PropertyListFragment = navHostFragment.childFragmentManager.fragments[0] as PropertyListFragment

            Log.v("fragment",fragment.toString())

            value?.forEach {
                Log.v("value each",it.city)
            }

            if (value != null) {
                fragment.updateList(value)
                Log.v("Ping","good")
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_item_detail)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}