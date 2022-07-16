package com.marina.surfgallery.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.marina.surfgallery.R
import com.marina.surfgallery.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        fillFields()
    }

    private fun fillFields() {

    }
}