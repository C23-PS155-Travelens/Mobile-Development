package com.app.travellens.view.camera

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.travellens.databinding.FragmentMainBinding
import com.app.travellens.model.response.ResponsePredict
import com.app.travellens.network.ApiClient
import com.app.travellens.viewmodel.ViewModelApps
import com.bumptech.glide.Glide
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    lateinit var viewModel : ViewModelApps
    private val binding get() = _binding!!

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ViewModelApps::class.java)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener {
            uploadImage()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireActivity().application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                "com.app.travellens",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }



    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
//              Silakan gunakan kode ini jika mengalami perubahan rotasi
//              rotateFile(file)
                getFile = file
                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                getFile = myFile
                binding.previewImageView.setImageURI(uri)
            }
        }
    }

    private fun uploadImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val requestImageFile = file.asRequestBody("image/*".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            viewModel.postImage().observe(viewLifecycleOwner) {
                if (it != null) {
                    Log.d("TAG", "uploadImage: ${it.predictedLabel}")
                    binding.txtNamaWisata.text = it.predictedLabel.toString()
                } else {
                    Log.d("TAG", "uploadImage: ${it?.predictedLabel}")
                }
            }
            viewModel.uploadGambar(imageMultipart)
        }
    }

}
