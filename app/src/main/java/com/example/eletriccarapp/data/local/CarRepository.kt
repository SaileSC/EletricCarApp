package com.example.eletriccarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_BATERIA
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_POTENCIA
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_PRECO
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_RECARGA
import com.example.eletriccarapp.data.local.CarrosContract.CarEntry.COLUMN_NAME_UR_PHOTO
import com.example.eletriccarapp.domain.Carro

class CarRepository(private val context : Context) {
    fun save(carro : Carro) : Boolean {
        var isSaved = false

        try{
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, carro.id)
                put(COLUMN_NAME_PRECO, carro.preco)
                put(COLUMN_NAME_BATERIA, carro.bateria)
                put(COLUMN_NAME_POTENCIA, carro.potencia)
                put(COLUMN_NAME_RECARGA, carro.recarga)
                put(COLUMN_NAME_UR_PHOTO, carro.urlPhoto)
            }

           val inserted = db?.insert(CarrosContract.CarEntry.TABLE_NAME, null, values)

            if(inserted != null){
                isSaved = true
            }

        }catch(ex : Exception){
            Log.e("Erro ao inserir os dados -> ", ex.message.toString() )
        }

        return isSaved
    }

    fun findCarById(id : Int) : Carro{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        //Colunas a serem exibidas, resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_UR_PHOTO
        )

        val filter = "${COLUMN_NAME_CAR_ID} = ?"

        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //Nome da tabela
            columns, //Colunas as resem exibidas
            filter, // where (filtro)
            filterValues, // Valor do where , substituindo o valor de ?
            null,
            null,
            null
        )


        var carId = ID_WHEN_NO_CAR
        var preco = ""
        var bateria = ""
        var potencia = ""
        var recarga = ""
        var urlPhoto = ""

        with(cursor){
            while(moveToNext()){
                carId = getString(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)).toInt()
                preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_UR_PHOTO))

                Log.d("FindById :  ->", "Carro ID : $carId encontrado no Banco de Dados...")
            }
        }

        cursor.close()
        return Carro(carId, preco, bateria, potencia, recarga, urlPhoto, true)
    }

    fun saveIfNotExist(carro : Carro){

        if(findCarById(carro.id).id == ID_WHEN_NO_CAR){
            save(carro)
        }
    }



    fun getAllCars() : List<Carro>{
        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase

        //Colunas a serem exibidas, resultado da Query
        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRECO,
            COLUMN_NAME_BATERIA,
            COLUMN_NAME_POTENCIA,
            COLUMN_NAME_RECARGA,
            COLUMN_NAME_UR_PHOTO
        )

        val cursor = db.query(
            CarrosContract.CarEntry.TABLE_NAME, //Nome da tabela
            columns, //Colunas as resem exibidas
            null, // where (filtro)
            null, // Valor do where , substituindo o valor de ?
            null,
            null,
            null
        )


        val listCarros = mutableListOf<Carro>()

        with(cursor){
            while(moveToNext()){
                val carId = getString(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID)).toInt()
                val preco = getString(getColumnIndexOrThrow(COLUMN_NAME_PRECO))
                val bateria = getString(getColumnIndexOrThrow(COLUMN_NAME_BATERIA))
                val potencia = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCIA))
                val recarga = getString(getColumnIndexOrThrow(COLUMN_NAME_RECARGA))
                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_UR_PHOTO))

                listCarros.add(
                    Carro(
                        carId,
                        preco,
                        bateria,
                        potencia,
                        recarga,
                        urlPhoto,
                        true)
                )
            }
        }

        cursor.close()
        return listCarros

    }

    companion object {
        const val ID_WHEN_NO_CAR = -1
    }
}