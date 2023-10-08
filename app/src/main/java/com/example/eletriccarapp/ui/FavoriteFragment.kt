package com.example.eletriccarapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.data.CarFactory
import com.example.eletriccarapp.data.local.CarRepository
import com.example.eletriccarapp.domain.Carro
import com.example.eletriccarapp.ui.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FavoriteFragment : Fragment() {
    lateinit var listaCarrosFavoritos : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorite_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }

    override fun onResume() {
        super.onResume()
        setupList()
    }

    private fun getCarOnLocalDb(): List<Carro> {
        val repository = CarRepository(requireContext())
        val carList = repository.getAllCars()
        return carList
    }


    fun setupList(){
        val cars = getCarOnLocalDb()
        val carroAdapter = CarAdapter(cars, true)
        listaCarrosFavoritos.apply {
            isVisible = true
            adapter = carroAdapter
        }

        carroAdapter.carItemLister = {
            //@TODO IMPLEMENTAR O DELETE NO BANCO DE DADOS
        }
    }

    fun setupView(view : View){
        view.apply {
            listaCarrosFavoritos = findViewById(R.id.rv_lista_carros_favoritos)
        }
    }
}