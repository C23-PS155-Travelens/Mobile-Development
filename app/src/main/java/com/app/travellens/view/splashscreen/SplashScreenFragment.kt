package com.app.travellens.view.splashscreen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.app.travellens.R
import com.app.travellens.databinding.FragmentSplashScreenBinding
import com.app.travellens.datastore.SharedPref

class SplashScreenFragment : Fragment() {
    lateinit var binding : FragmentSplashScreenBinding
    private lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        startSplashScreen()
    }

    private fun startSplashScreen() {
        sharedPref.getId.asLiveData().observe(viewLifecycleOwner){
            Handler(Looper.getMainLooper()).postDelayed({
                if (it.equals("Undefined")){
                    Log.d("token pas function gettoken", it)
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_loginFragment)
                }else{
                    Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_homeFragment)
                }
            }, 1000)
        }
    }
}