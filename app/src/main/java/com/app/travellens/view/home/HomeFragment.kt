package com.app.travellens.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.travellens.adapter.HomeAdapter
import com.app.travellens.databinding.FragmentHomeBinding
import com.app.travellens.datastore.SharedPref
import com.app.travellens.model.request.DataWisata
import com.app.travellens.viewmodel.ViewModelApps


class HomeFragment : Fragment() {
    lateinit var binding : FragmentHomeBinding
    lateinit var adapter : HomeAdapter
    lateinit var viewModel : ViewModelApps
    lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPref(requireContext())

        sharedPref.getNama.asLiveData().observe(viewLifecycleOwner){
            if (it != null){
                binding.txtUsername.text = "Halo, $it"
            }
        }

        viewModel = ViewModelProvider(this).get(ViewModelApps::class.java)
        viewModel.getDataWisata().observe(viewLifecycleOwner){dataWisata ->
            if (dataWisata != null){
                binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                var dataWisataMapped = dataWisata.map { dataWisata ->
                    DataWisata(dataWisata.alamat, dataWisata.deskripsi,dataWisata.id,dataWisata.lokasi,dataWisata.nama) }
                adapter = HomeAdapter(dataWisataMapped)
                binding.recyclerView.adapter = adapter
            }else{
                binding.recyclerView.visibility = View.GONE
            }
        }
        viewModel.getWisata()

    }
}