package com.kotlin.albert_p2_ap2.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GastosDto(
    var idGasto: Int? = null,
    @Json(name = "fecha")
    var fecha: Date,
    var idSuplidor: Int? = null,
    var suplidor: String = "",
    var ncf: String = "",
    var concepto: String = "",
    var descuento: Int = 0,
    var itbis: Int = 0,
    var monto: Int = 0
)