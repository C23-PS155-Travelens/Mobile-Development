package com.app.travellens.view.authentication.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.travellens.R
import com.app.travellens.databinding.FragmentLoginBinding
import com.app.travellens.datastore.SharedPref
import com.app.travellens.viewmodel.ViewModelApps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    lateinit var binding : FragmentLoginBinding
    lateinit var viewModel : ViewModelApps
    private lateinit var sharedPref: SharedPref
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(requireContext())

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelApps::class.java)

        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener{
            val username = binding.editTextUsername.text.toString()
            val password = binding.editTextPassword.text.toString()
//            suspend fun saveLog(ids : String, usernames : String, emails : String, photos : String, addresss : String, phones : String){
            viewModel.userLogin().observe(viewLifecycleOwner){
                if (it != null){
                    if (it.status.equals("success")){
//                        Log.d("Data ID", "onViewCreated: ${it.data.id}")
//                        Log.d("Data Address", "onViewCreated: ${it.data.address}")
//                        Log.d("Data Email", "onViewCreated: ${it.data.email}")
//                        Log.d("Data Phone", "onViewCreated: ${it.data.phone}")
//                        Log.d("Data Photo", "onViewCreated: ${it.data.photo}")
//                        Log.d("Data Username", "onViewCreated: ${it.data.username}")
                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        saveSession(it.data.id.toString(), it.data.username, it.data.email, it.data.photo, it.data.address, it.data.phone)
                        Toast.makeText(context,"Login berhasil!",Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(context,"Username atau password salah",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            viewModel.login(username,password)
        }
    }

    private fun saveSession(id : String, username: String, email: String, photo: String, address: String, phone: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            sharedPref.saveLog(id, username, email, photo, address, phone)
        }
    }
}