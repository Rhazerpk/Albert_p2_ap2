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

    @GET("/api/gastos")
    suspend fun getGastos(): List<GastosDto>

    @GET("/api/gastos/{id}")
    suspend fun getGastosId(@Path("id") id: Int): GastosDto

    @POST("/api/gastos")
    suspend fun postGastos(@Body gastos: GastosDto): Response<GastosDto>
    @DELETE("/api/gastos/{id}")
    suspend fun deleteGastos(@Path("id") id: Int): Response<Unit>
    @PUT("/api/gastos/{id}")
    suspend fun putGastos(@Path("id") id: Int, @Body gastos: GastosDto): Response<Unit>
}