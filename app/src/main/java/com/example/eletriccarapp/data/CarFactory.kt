package com.example.eletriccarapp.data

import com.example.eletriccarapp.domain.Carro

// API cars https://igorbag.github.io/cars-api/cars.json

object CarFactory {
    val list = listOf(
        Carro(
            id = 1,
            preco = "R$ 300.000,00",
            bateria = "300 kWh",
            potencia = "200cv",
            recarga = "30 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false
        ),
        Carro(
            id = 2,
            preco = "R$ 200.000,00",
            bateria = "200 kWh",
            potencia = "150cv",
            recarga = "30 min",
            urlPhoto = "www.google.com.br",
            isFavorite = false
        )
    )
}