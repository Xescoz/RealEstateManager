package com.openclassrooms.realestatemanager.ui

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.FragmentItemListBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.room.PropertyApplication
import com.openclassrooms.realestatemanager.room.PropertyViewModel
import com.openclassrooms.realestatemanager.room.PropertyViewModelFactory

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

class PropertyListFragment : Fragment() {

    private lateinit var adapter: PropertyListRecyclerViewAdapter

    private var isUpdate :Boolean = false

    private val propertyViewModel: PropertyViewModel by viewModels {
        PropertyViewModelFactory((activity?.application as PropertyApplication).propertyRepository,(activity?.application as PropertyApplication).agentRepository)
    }

    private var _binding: FragmentItemListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.itemList

        Log.v("Ping fragment","onViewCreated")

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as Property

            val bundle = bundleOf("item" to item)

            val action = PropertyListFragmentDirections.showItemDetail().setProperty(item)

            if (binding.itemDetailNavContainer != null) {
                binding.itemDetailNavContainer?.findNavController()
                        ?.navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(action)
            }
        }


        setupRecyclerView(recyclerView, onClickListener)
    }

    private fun setupRecyclerView(
            recyclerView: RecyclerView,
            onClickListener: View.OnClickListener
    ) {
        Log.v("Ping fragment","setupRecyclerView")

        adapter = PropertyListRecyclerViewAdapter(
                onClickListener,
                requireContext())
        recyclerView.adapter = adapter

        /*propertyViewModel.getAllProperties().observeOnce(viewLifecycleOwner, { propertyList ->

            adapter.listProperty = propertyList

        })*/



        propertyViewModel.getAllProperties().observe(viewLifecycleOwner, { propertyList ->

            Log.v("Ping fragment","observe")
            if(!isUpdate)
                adapter.listProperty = propertyList

        })
    }

    override fun onPause() {
        super.onPause()
        isUpdate = false
    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    fun updateList(propertyList: ArrayList<Property>){
        isUpdate = true
        adapter.listProperty = propertyList
        Log.v("Ping Fragment","good")
        Log.v("List Size Fragment", propertyList.size.toString())

        Log.v("adapter size",adapter.itemCount.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}