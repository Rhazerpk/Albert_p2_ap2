package com.kotlin.albert_p2_ap2.util

import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto

data class GastosListState(
    val isLoading: Boolean = false,
    val gastos: List<GastosDto> = emptyList(),
    val error: String = ""
)

data class GastoState(
    val isLoading: Boolean = false,
    val gastos: GastosDto? = null,
    val error: String = "",
)