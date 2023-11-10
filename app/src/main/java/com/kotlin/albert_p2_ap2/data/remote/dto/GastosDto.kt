package com.kotlin.albert_p2_ap2.data.remote.dto

data class GastosDto(
    val idGasto: Int?=null,
    var fecha: String = "",
    var ncf: String = "",
    var idSuplidor: Int?= null,
    var suplidor:String = "",
    var concepto: String = "",
    var descuento: Int?= null,
    var itbis: Int?= null,
    var monto: Int?= null

)