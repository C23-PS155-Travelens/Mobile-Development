package com.app.travellens.view.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.app.travellens.R
import com.app.travellens.databinding.FragmentEditProfileBinding
import com.app.travellens.datastore.SharedPref
import com.app.travellens.view.camera.uriToFile
import com.app.travellens.viewmodel.ViewModelApps
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class EditProfileFragment : Fragment() {
    lateinit var binding : FragmentEditProfileBinding
    lateinit var sharedPref : SharedPref
    lateinit var viewModel  : ViewModelApps
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPref(requireContext())
        viewModel = ViewModelProvider(this)[ViewModelApps::class.java]

        binding.btnUbahData.setOnClickListener{
            val nama = binding.editTextNama.text.toString()
            val email = binding.editTextEmail.text.toString()
            val phone = binding.editTextNotelp.text.toString()
            val address = binding.editTextAlamat.text.toString()
            val password = binding.editTextPassword.text.toString()
            val passwordConfirm = binding.editTextConfirmPassword.text.toString()

            viewModel.updateProfile().observe(viewLifecycleOwner){
                if (it != null){
                    saveSession(it.username, it.email, it.photo, it.address, it.phone)
                    Toast.makeText(context, "Berhasil Update Profile!", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment2)
                }else{
                    Toast.makeText(context, "Gagal Update Profile!", Toast.LENGTH_SHORT).show()
                }
            }

            sharedPref.getId.asLiveData().observe(viewLifecycleOwner){
                if (it != null){
                    viewModel.updateProfileData(nama, email, address, phone, it.toInt())
                    Log.d("id", it.toString())
                }
            }
        }

        binding.ivProfile.setOnClickListener{
            startGallery()
        }

        binding.btnBack.setOnClickListener{
            findNavController().navigateUp()
        }


        bind()

        binding.btnBack
    }

    fun bind(){
        sharedPref.getNama.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                binding.editTextNama.setText(it)
            }
        }
        sharedPref.getEmail.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                binding.editTextEmail.setText(it)
            }
        }
        sharedPref.getPhone.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                binding.editTextNotelp.setText(it)
            }
        }
        sharedPref.getAddress.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                binding.editTextAlamat.setText(it)
            }
        }
    }

    private fun saveSession(username: String, email: String, photo: String, address: String, phone: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            sharedPref.saveAfterUpdate(username, email, photo, address, phone)
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                binding.ivProfile.setImageURI(uri)
            }
        }
    }


}