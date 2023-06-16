package com.app.travellens.view.authentication.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.app.travellens.R
import com.app.travellens.databinding.FragmentRegisterBinding
import com.app.travellens.viewmodel.ViewModelApps

class RegisterFragment : Fragment() {
    lateinit var binding : FragmentRegisterBinding
    lateinit var viewModel : ViewModelApps


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelApps::class.java)

        binding.btnDaftar.setOnClickListener{
            val username = binding.editTextNamaLengkap.text.toString()
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            val ulangiPassword = binding.editTextUlangiPassword.text.toString()

            viewModel.userRegister().observe(viewLifecycleOwner){
                if (it != null){
                    if (it.message.equals("Registrasi berhasil!")){
                        Log.d("Data Message", "onViewCreated: ${it.message}")
//                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        Toast.makeText(context,"Registrasi berhasil!",Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                    }else{
                        Toast.makeText(context,"Password dan konfirmasi password tidak cocok",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            viewModel.register(username,email,password,ulangiPassword)
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }
    }
}