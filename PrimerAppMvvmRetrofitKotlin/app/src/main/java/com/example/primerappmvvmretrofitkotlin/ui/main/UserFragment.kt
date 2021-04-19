package com.example.primerappmvvmretrofitkotlin.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.demo.core.Resource
import com.example.primerappmvvmretrofitkotlin.R
import com.example.primerappmvvmretrofitkotlin.data.model.User
import com.example.primerappmvvmretrofitkotlin.data.remote.UserDataSource
import com.example.primerappmvvmretrofitkotlin.databinding.FragmentUserBinding
import com.example.primerappmvvmretrofitkotlin.presentation.UserViewModel
import com.example.primerappmvvmretrofitkotlin.presentation.UserViewModelFactory
import com.example.primerappmvvmretrofitkotlin.repository.RetrofitClient
import com.example.primerappmvvmretrofitkotlin.repository.UserRepositoryImpl
import com.example.primerappmvvmretrofitkotlin.ui.main.adapters.UsersAdapter


class UserFragment : Fragment(R.layout.fragment_user), UsersAdapter.OnUserClickListener {
    private lateinit var binding: FragmentUserBinding
    private val viewModel by viewModels<UserViewModel> { UserViewModelFactory(UserRepositoryImpl(
        UserDataSource(RetrofitClient.webservice)
    )) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserBinding.bind(view)
        setupSearchView()
        setupObservers()

        binding.btnReset.setOnClickListener{
            setupObservers()
            binding.btnReset.visibility=View.GONE
        }
    }

    private fun setupObservers() {

        viewModel.fetchUsers().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    //binding.rvUsers.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
                    binding.rvUsers.adapter = UsersAdapter(it.data.results, this@UserFragment)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: $it.exception ")
                    mostrarError("${it.exception}")
                }
            }
        })

        viewModel.usersSearchLiveData.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvUsers.adapter = state.data?.let { UsersAdapter(it.results, this@UserFragment) }
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: $state.exception ")
                    mostrarError("${state.exception}")
                }
            }
        })



    }

    override fun onUserClick(user: User) {
         Toast.makeText(requireContext(),"Hola: ${user.name}",Toast.LENGTH_SHORT).show()
        val action = UserFragmentDirections.actionUserFragmentToDetailUserFragment(user.name, user.email, user.image,user.phone)
        findNavController().navigate(action)
    }

    private fun setupSearchView(){
        binding.mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchUser(query!!)
                binding.btnReset.visibility=View.VISIBLE
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })
    }

    private fun mostrarError(mensaje: String){
        Toast.makeText(requireContext(), mensaje, Toast.LENGTH_SHORT).show()
    }

}