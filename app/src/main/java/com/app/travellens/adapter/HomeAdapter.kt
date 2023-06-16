package com.app.travellens.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.app.travellens.R
import com.app.travellens.databinding.ItemListBinding
import com.app.travellens.model.request.DataWisata
import com.bumptech.glide.Glide

class HomeAdapter(private val dataWisata : List<DataWisata>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    class ViewHolder (val binding : ItemListBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataWisata.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.txtNama.text = dataWisata[position].nama
        val fullDescription = dataWisata[position].deskripsi
        val maxLength = 90 // Maximum number of characters to show

        if (fullDescription.length > maxLength) {
            val shortDescription = fullDescription.substring(0, maxLength) + "..."
            holder.binding.txtDeskripsi.text = shortDescription
        } else {
            holder.binding.txtDeskripsi.text = fullDescription
        }

        holder.binding.card.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("nama", dataWisata[position].nama)
            bundle.putString("deskripsi", dataWisata[position].deskripsi)
            bundle.putString("alamat", dataWisata[position].alamat)
            bundle.putString("lokasi", dataWisata[position].lokasi)
            bundle.putString("id", dataWisata[position].id.toString())
//            bundle.putString("gambar", dataWisata[position].gambar)
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_detailWisataFragment, bundle)
        }
    }

}