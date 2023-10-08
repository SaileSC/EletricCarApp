package com.example.eletriccarapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.domain.Carro

class CarAdapter(private val carros : List<Carro>, private val isFavoriteScreen : Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>(){

    var carItemLister : (Carro) -> Unit = {}

    //Cria uma nova view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.carro_item, parent, false)
        return ViewHolder(view)
    }

    //Pega o conteudo da view e troca pela informacao de item de uma lista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.apply {
            preco.text = carros[position].preco
            bateria.text = carros[position].bateria
            potencia.text = carros[position].potencia
            recarga.text = carros[position].recarga
            if(isFavoriteScreen){
                favorite.setImageResource(R.drawable.ic_star_selected)
            }

            favorite.setOnClickListener{
                val carro = carros[position]
                carItemLister(carro)
                setupFavotite(carro)
            }
        }
    }

fun ViewHolder.setupFavotite(carro: Carro) {
        carro.isFavorite = !carro.isFavorite
        if (carro.isFavorite) {
            favorite.setImageResource(R.drawable.ic_star_selected)
        } else {
            favorite.setImageResource(R.drawable.ic_star)
        }
    }

    //prega a quantidade de carros da lista
    override fun getItemCount(): Int = carros.size
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val preco : TextView
        val bateria : TextView
        val potencia : TextView
        val recarga : TextView
        val favorite : ImageView

        init {
            view.apply{
                preco = findViewById(R.id.tv_preco_value)
                bateria = findViewById(R.id.tv_bateria_value)
                potencia = findViewById(R.id.tv_potencia_value)
                recarga = findViewById(R.id.tv_recarga_value)
                favorite = findViewById(R.id.iv_favorite)
            }
        }

    }
}




