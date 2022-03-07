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

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    private val unhandledKeyEventListenerCompat = ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
        if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
            Toast.makeText(
                    v.context,
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show()
        } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
            Toast.makeText(
                    v.context,
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show()
        }
        false
    }

    private lateinit var adapter: PropertyListRecyclerViewAdapter

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

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        /** Click Listener to trigger navigation based on if you have
         * a single pane layout or two pane layout
         */
        val onClickListener = View.OnClickListener { itemView ->
            val item = itemView.tag as Property

            val bundle = bundleOf("item" to item)

            val action = PropertyListFragmentDirections.showItemDetail().setProperty(item)

            if (itemDetailFragmentContainer != null) {
                itemDetailFragmentContainer.findNavController()
                        .navigate(R.id.fragment_item_detail, bundle)
            } else {
                itemView.findNavController().navigate(action)
            }
        }

        /**
         * Context click listener to handle Right click events
         * from mice and trackpad input to provide a more native
         * experience on larger screen devices
         */
        val onContextClickListener = View.OnContextClickListener { v ->
            val item = v.tag as Property
            Toast.makeText(
                    v.context,
                    "Context click of item " + item.id,
                    Toast.LENGTH_LONG
            ).show()
            true
        }

        setupRecyclerView(recyclerView, onClickListener, onContextClickListener)
    }

    private fun setupRecyclerView(
            recyclerView: RecyclerView,
            onClickListener: View.OnClickListener,
            onContextClickListener: View.OnContextClickListener
    ) {

        propertyViewModel.allProperty.observe(viewLifecycleOwner, { propertyList ->

            adapter = context?.let {
                PropertyListRecyclerViewAdapter(
                        propertyList,
                        onClickListener,
                        onContextClickListener,
                        it.applicationContext
                )
            }!!
            recyclerView.adapter = adapter
        })
    }

    fun updateList(propertyList: ArrayList<Property>){
        adapter.updateList(propertyList)
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}