package com.kotlin.albert_p2_ap2.data.remote

import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.PUT

interface GastosApi {

    @GET("api/Gastos")
    suspend fun getGastos(): List<GastosDto>

    @POST("api/Gastos")
    suspend fun postGastos(@Body gastosDto: GastosDto): Response<GastosDto>

    @GET("api/Gastos/{id}")
    suspend fun getGastosById(@Path("id") id: Int): GastosDto

    @PUT("api/Gastos/{id}")
    suspend fun putGastos(@Path("id") id: Int, @Body gastos: GastosDto): Response<GastosDto>

    @DELETE("api/Gastos/{id}")
    suspend fun deleteGastos(@Path("id") gastoId: Int): Response<GastosDto>
}