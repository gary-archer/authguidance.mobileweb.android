package com.authguidance.mobilewebview.views.headings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.authguidance.mobilewebview.databinding.FragmentMenuBinding

/*
 * The menu fragment shows operations for invoking secured web content
 */
class MenuFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentMenuBinding

    /*
     * Initialise the view
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.binding = FragmentMenuBinding.inflate(inflater, container, false)

        // Create this fragment's view model
        this.binding.model = MenuViewModel()
        return binding.root
    }
}
