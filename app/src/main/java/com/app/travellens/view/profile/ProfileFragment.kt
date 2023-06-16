package com.app.travellens.view.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.travellens.R
import com.app.travellens.databinding.FragmentProfileBinding
import com.app.travellens.datastore.SharedPref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    lateinit var binding : FragmentProfileBinding
    lateinit var sharedPref : SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        binding.tvNamaUser.text.let {
            sharedPref.getNama.asLiveData().observe(viewLifecycleOwner){
                if (it != null){
                    binding.tvNamaUser.text = "Halo, $it"
                }
            }
        }
        binding.tvEmailUser.text.let {
            sharedPref.getEmail.asLiveData().observe(viewLifecycleOwner){
                if (it != null){
                    binding.tvEmailUser.text = it
                }
            }
        }

        binding.btnLogout.setOnClickListener{
            removeToken()
            Toast.makeText(context, "Berhasil Logout!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_profileFragment2_to_loginFragment)
        }


        binding.navigateToProfile.setOnClickListener{
            findNavController().navigate(R.id.action_profileFragment2_to_editProfileFragment)
        }
    }


    private fun removeToken() {
        lifecycleScope.launch(Dispatchers.IO) {
            sharedPref.removeSession()
        }
    }
}