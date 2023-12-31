package com.kotlin.albert_p2_ap2.data.remote.dto

import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GastosDto(
    @PrimaryKey
    @Json(name = "idGasto")
    val idGasto : Int?,
    @Json(name = "fecha")
    val fecha: String?,
    @Json(name = "idSuplidor")
    val idSuplidor : Int? = null,
    @Json(name = "suplidor")
    val suplidor : String?,
    @Json(name="concepto")
    val concepto: String,
    @Json(name="ncf")
    val ncf: String?,
    @Json(name="itbis")
    val itbis: Int?,
    @Json(name="monto")
    val monto: Int?,

)