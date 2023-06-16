package com.app.travellens.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.travellens.databinding.FragmentDetailWisataBinding

class DetailWisataFragment : Fragment() {
    lateinit var binding : FragmentDetailWisataBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailWisataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        val nama = arguments?.getString("nama")
        val alamat = arguments?.getString("alamat")
        val deskripsi = arguments?.getString("deskripsi")
        val lokasi = arguments?.getString("lokasi")

        binding.namaTempat.text = nama
        binding.namaLokasi.text = alamat
        binding.deskripsiWisata.text = deskripsi


    }
}