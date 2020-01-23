package com.authguidance.mobilewebview.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.authguidance.mobilewebview.databinding.FragmentHostBinding

/*
 * The host fragment for the web view
 */
class HostFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentHostBinding

    /*
     * Inflate the view
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        this.binding = FragmentHostBinding.inflate(inflater, container, false)
        return this.binding.root
    }
}
