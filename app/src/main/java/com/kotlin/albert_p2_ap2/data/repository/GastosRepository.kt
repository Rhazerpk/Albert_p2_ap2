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
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }

    fun getGastosId(id: Int): Flow<Resource<GastosDto>> = flow {
        try {
            emit(Resource.Loading())
            val gastos = api.getGastosId(id)

            emit(Resource.Success(gastos))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Error HTTP GENERAL"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "verificar tu conexion a internet"))
        }
    }


    suspend fun deleteGastos(id: Int) {
        api.deleteGastos(id)
    }
    suspend fun postGastos(gastosDto: GastosDto) {
        api.postGastos(gastosDto)
    }
    suspend fun putGastos(id: Int,gastosDto: GastosDto) {
        api.putGastos(id, gastosDto)
    }

}