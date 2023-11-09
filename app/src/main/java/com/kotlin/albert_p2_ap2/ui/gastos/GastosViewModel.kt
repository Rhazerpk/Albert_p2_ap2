package com.kotlin.albert_p2_ap2.ui.gastos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto
import com.kotlin.albert_p2_ap2.data.repository.GastosRepository
import com.kotlin.albert_p2_ap2.util.GastosListState
import com.kotlin.albert_p2_ap2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class GastosViewModel @Inject constructor(
    private val gastosRepository: GastosRepository
): ViewModel(){

    var fecha by mutableStateOf(Date())
    var suplidor by mutableStateOf("")
    var ncf by mutableStateOf("")
    var concepto by mutableStateOf("")
    var descuento by mutableStateOf(0)
    var itbis by mutableStateOf(0)
    var monto by mutableStateOf(0)

    var isValidNcf by mutableStateOf(true)
    var isValidSuplidor by mutableStateOf(true)
    var isValidConcepto by mutableStateOf(true)
    var isValidDescuento by mutableStateOf(true)
    var isValidItbis by mutableStateOf(true)
    var isValidMonto by mutableStateOf(true)

    fun isValid(): Boolean {
        isValidSuplidor = suplidor.isNotBlank()
        isValidNcf = ncf.isNotBlank()
        isValidConcepto = concepto.isNotBlank()
        isValidItbis = itbis > 0
        isValidMonto = monto > 0
        isValidDescuento = descuento > 0
        return isValidSuplidor && isValidNcf && isValidConcepto && isValidItbis && isValidItbis && isValidMonto
    }

    private val _uiState = MutableStateFlow(GastosListState())
    val uiState: StateFlow<GastosListState> = _uiState.asStateFlow()

    private val _isMessageShown = MutableSharedFlow<Boolean>()
    val isMessageShownFlow = _isMessageShown.asSharedFlow()

    fun setMessageShown() {
        viewModelScope.launch {
            _isMessageShown.emit(true)
        }
    }

    init {
        gastosRepository.getGastos().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.update { it.copy(isLoading = true) }
                }

                is Resource.Success -> {
                    _uiState.update { it.copy(gastos = result.data ?: emptyList()) }
                }

                is Resource.Error -> {
                    _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveGasto() {
        viewModelScope.launch {
            if (isValid()) {
                val gastosDto = GastosDto(
                    fecha = fecha,
                    suplidor = suplidor,
                    ncf = ncf,
                    concepto = concepto,
                    descuento = descuento,
                    itbis = itbis,
                    monto = monto,
                )
                gastosRepository.postGastos(gastosDto)
                limpiar()
            }
        }
    }

    fun getGastoById(id: Int) {
        viewModelScope.launch {
            gastosRepository.getGastosById(id).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        val gasto = result.data
                        fecha = fecha
                        suplidor = suplidor
                        ncf = ncf
                        concepto = concepto
                        descuento = descuento
                        itbis = itbis
                        monto = monto
                    }

                    is Resource.Error -> {
                        _uiState.update { it.copy(error = result.message ?: "Error desconocido") }
                    }
                }
            }.launchIn(viewModelScope)
        }
    }


    fun editGasto(id: Int) {
        viewModelScope.launch {
            if (isValid()) {
                val gastoEditado = GastosDto(
                    fecha = fecha,
                    suplidor = suplidor,
                    ncf = ncf,
                    concepto = concepto,
                    descuento = descuento,
                    itbis = itbis,
                    monto = monto
                )
                try {
                    gastosRepository.putGastos(id, gastoEditado)
                } catch (e: Exception) {
                   e.message
                }
            }
        }
    }

    fun deleteGastos(id: Int) {
        viewModelScope.launch {
            try {
                gastosRepository.deleteGastos(id)
            } catch (e: Exception){
                e.message
            }
        }
    }

    fun limpiar() {
        fecha = Date()
        suplidor = ""
        ncf = ""
        concepto = ""
        descuento = 0
        itbis = 0
        monto = 0
    }

}