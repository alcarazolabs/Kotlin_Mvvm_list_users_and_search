package com.example.primerappmvvmretrofitkotlin.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.primerappmvvmretrofitkotlin.R
import com.example.primerappmvvmretrofitkotlin.databinding.FragmentDetailUserBinding


class DetailUserFragment : Fragment(R.layout.fragment_detail_user) {
    private val args by navArgs<DetailUserFragmentArgs>()
    private lateinit var binding: FragmentDetailUserBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailUserBinding.bind(view)

        Glide.with(requireContext()).load(args.image).centerCrop().into(binding.imgDetailUser)
        binding.txtNameUserDetail.text = args.name
        binding.txtEmailUserDetail.text = "Email: ${args.email}"
        binding.txtPhoneUserDetail.text = "Phone: ${args.phone}"
    }
}