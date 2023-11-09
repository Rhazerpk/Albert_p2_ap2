package com.kotlin.albert_p2_ap2.data.repository

import com.kotlin.albert_p2_ap2.data.remote.GastosApi
import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto
import com.kotlin.albert_p2_ap2.util.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import retrofit2.HttpException

class GastosRepository @Inject constructor(
    private val api: GastosApi
) {
    fun getGastos(): Flow<Resource<List<GastosDto>>> = flow {
        try {
            emit(Resource.Loading())

            val gastos = api.getGastos()

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

    fun getGastosById(id: Int): Flow<Resource<GastosDto>> = flow {
        try {
            emit(Resource.Loading())

            val gasto = api.getGastosById(id)

            emit(Resource.Success(gasto))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

    fun postGastos(gasto: GastosDto): Flow<Resource<GastosDto?>> = flow {
        try {
            emit(Resource.Loading())

            val response = api.postGastos(gasto)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error("Error al crear un gasto"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

    fun deleteGastos(id: Int): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())

            val response = api.deleteGastos(id)

            if (response.isSuccessful) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Error al eliminar un gasto"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

    fun putGastos(id: Int, gastos: GastosDto): Flow<Resource<GastosDto?>> = flow {
        try {
            emit(Resource.Loading())

            val response = api.putGastos(id, gastos)

            if (response.isSuccessful) {
                emit(Resource.Success(response.body()))
            } else {
                emit(Resource.Error("Error al modificar un gasto"))
            }
        } catch (e: IOException) {
            emit(Resource.Error("Verificar tu conexión a internet"))
        }
    }

}