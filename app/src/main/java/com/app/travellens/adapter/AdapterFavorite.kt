package com.app.travellens.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.travellens.R
import com.app.travellens.databinding.WishListBinding
import com.app.travellens.model.request.DataWisata
import com.bumptech.glide.Glide
import com.example.navigasidanapi.room.Favorite

class AdapterFavorite(var dataWisata : List<DataWisata>) : ListAdapter<Favorite, AdapterFavorite.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Favorite> =
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(oldUser: Favorite, newUser: Favorite): Boolean {
                    return oldUser.username == newUser.username
                }

                override fun areContentsTheSame(oldUser: Favorite, newUser: Favorite): Boolean {
                    return oldUser == newUser
                }
            }
    }
    class ViewHolder(val binding: WishListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = WishListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataWisata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtDeskripsi.text = dataWisata[position].deskripsi
        holder.binding.txtNama.text = dataWisata[position].nama

        holder.binding.card.setOnClickListener {
            holder.binding.card.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("nama", dataWisata[position].nama)
                bundle.putString("deskripsi", dataWisata[position].deskripsi)
                bundle.putString("alamat", dataWisata[position].alamat)
                bundle.putString("lokasi", dataWisata[position].lokasi)
                bundle.putString("id", dataWisata[position].id.toString())
                Navigation.findNavController(it)
                    .navigate(R.id.action_wishListFragment_to_detailWishListFragment, bundle)
            }
        }
    }
}