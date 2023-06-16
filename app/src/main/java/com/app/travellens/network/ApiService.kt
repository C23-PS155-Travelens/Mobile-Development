package com.app.travellens.network

import com.app.travellens.model.response.ResponseGetWisataItem
import com.app.travellens.model.request.Login
import com.app.travellens.model.request.Register
import com.app.travellens.model.request.UpdateProfile
import com.app.travellens.model.response.ResponsePredict
import com.app.travellens.model.response.ResponseRegister
import com.app.travellens.model.response.ResponseUpdateProfileImage
import com.app.travellens.model.response.ResponseUserLogin
import com.app.travellens.model.response.ResponseUsrUpdateProfile
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @POST("login")
    fun login(@Body data : Login) : Call<ResponseUserLogin>

    @POST("register")
    fun register(@Body data : Register) : Call<ResponseRegister>

    @GET("wisata")
    fun getWisata() : Call<List<ResponseGetWisataItem>>

    @PUT("profile/edit/{id}")
    fun editProfile(@Body data : UpdateProfile, @Path("id") id : Int ) : Call<ResponseUsrUpdateProfile>

    @PUT("profile/edit/photo/{id}")
    @Multipart
    fun editPhoto(@Part image : MultipartBody.Part) : Call<ResponseUpdateProfileImage>

    @POST("predict")
    @Multipart
    fun uploadImage(@Part image : MultipartBody.Part) : Call<ResponsePredict>



}