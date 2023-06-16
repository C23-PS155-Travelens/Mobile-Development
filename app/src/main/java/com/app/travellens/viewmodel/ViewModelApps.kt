package com.app.travellens.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.travellens.model.response.ResponseGetWisataItem
import com.app.travellens.model.request.Login
import com.app.travellens.model.request.Register
import com.app.travellens.model.request.UpdateProfile
import com.app.travellens.model.response.ResponsePredict
import com.app.travellens.model.response.ResponseRegister
import com.app.travellens.model.response.ResponseUpdateProfileImage
import com.app.travellens.model.response.ResponseUserLogin
import com.app.travellens.model.response.ResponseUsrUpdateProfile
import com.app.travellens.network.ApiClient
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModelApps : ViewModel() {
    private val userLogin = MutableLiveData<ResponseUserLogin?>()
    private val userRegister = MutableLiveData<ResponseRegister?>()
    private val postImage = MutableLiveData<ResponsePredict?>()
    private val getDataWisata = MutableLiveData<List<ResponseGetWisataItem>?>()
    private val updateProfile = MutableLiveData<ResponseUsrUpdateProfile?>()
    private val updateProfileImage = MutableLiveData<ResponseUpdateProfileImage?>()

    fun userLogin() : MutableLiveData<ResponseUserLogin?>{
        return userLogin
    }

    fun userRegister() : MutableLiveData<ResponseRegister?> {
        return userRegister
    }

    fun postImage() : MutableLiveData<ResponsePredict?> {
        return postImage
    }

    fun getDataWisata() : MutableLiveData<List<ResponseGetWisataItem>?> {
        return getDataWisata
    }

    fun updateProfile() : MutableLiveData<ResponseUsrUpdateProfile?> {
        return updateProfile
    }

    fun updateProfileImage() : MutableLiveData<ResponseUpdateProfileImage?> {
        return updateProfileImage
    }

    fun login(username : String, password : String){
        ApiClient.instance1.login(Login(username, password)).enqueue(object :
            Callback<ResponseUserLogin> {
            override fun onResponse(
                call: Call<ResponseUserLogin>,
                response: Response<ResponseUserLogin>
            ) {
                if (response.isSuccessful){
                    userLogin.postValue(response.body())
                }else{
                    userLogin.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                userLogin.postValue(null)
            }
        })
    }


    fun register(username : String, email : String, password : String, confirm_password : String){
        ApiClient.instance1.register(Register(username,email, password, confirm_password)).enqueue(object :
            Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful){
                    userRegister.postValue(response.body())
                }else{
                    userRegister.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                userRegister.postValue(null)
            }
        })
    }

    fun uploadGambar(image : MultipartBody.Part){
        ApiClient.instance2.uploadImage(image).enqueue(object : Callback<ResponsePredict> {
            override fun onResponse(
                call: Call<ResponsePredict>,
                response: Response<ResponsePredict>
            ) {
                Log.d("Is Sucess", "onResponse: ${response.isSuccessful}")
                Log.d("Message", "onResponse: ${response.message()}")
                Log.d("Error Body", "onResponse: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    postImage.postValue(response.body())
                } else {
                    postImage.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponsePredict>, t: Throwable) {
                postImage.postValue(null)
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })
    }

    fun getWisata(){
        ApiClient.instance1.getWisata().enqueue(object : Callback<List<ResponseGetWisataItem>>{
            override fun onResponse(
                call: Call<List<ResponseGetWisataItem>>,
                response: Response<List<ResponseGetWisataItem>>
            ) {
                response.body()?.forEach {
                    Log.d("TAG", "Alamat: ${it.alamat}")

                }
                if(response.isSuccessful){
                    getDataWisata.postValue(response.body())
                }else{
                    getDataWisata.postValue(null)
                }
            }

            override fun onFailure(call: Call<List<ResponseGetWisataItem>>, t: Throwable) {
                getDataWisata.postValue(null)
            }

        })
    }


    fun updateProfileData(username : String, email : String, address : String, phone : String, id : Int){
        ApiClient.instance1.editProfile(UpdateProfile(username,email, address, phone ),id).enqueue(object :
            Callback<ResponseUsrUpdateProfile> {
            override fun onResponse(
                call: Call<ResponseUsrUpdateProfile>,
                response: Response<ResponseUsrUpdateProfile>
            ) {
                if (response.isSuccessful){
                    updateProfile.postValue(response.body())
                }else{
                    updateProfile.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUsrUpdateProfile>, t: Throwable) {
                updateProfile.postValue(null)
            }
        })
    }

    fun ubahGambar(image : MultipartBody.Part){
        ApiClient.instance1.editPhoto(image).enqueue(object : Callback<ResponseUpdateProfileImage> {
            override fun onResponse(
                call: Call<ResponseUpdateProfileImage>,
                response: Response<ResponseUpdateProfileImage>
            ) {
                Log.d("Is Sucess", "onResponse: ${response.isSuccessful}")
                Log.d("Message", "onResponse: ${response.message()}")
                Log.d("Error Body", "onResponse: ${response.errorBody()?.string()}")

                if (response.isSuccessful) {
                    updateProfileImage.postValue(response.body())
                } else {
                    updateProfileImage.postValue(null)
                }
            }

            override fun onFailure(call: Call<ResponseUpdateProfileImage>, t: Throwable) {
                updateProfileImage.postValue(null)
                Log.d("TAG", "onFailure: ${t.message}")
            }
        })

    }
}