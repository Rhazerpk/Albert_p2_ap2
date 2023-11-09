package com.kotlin.albert_p2_ap2.ui.gastos

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Register(viewModel: GastosViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    var invalidDate by remember { mutableStateOf(true) }

    var date by remember { mutableStateOf("") }
    @Suppress("DEPRECATION") val datePickerDialog = DatePickerDialog(
        context, { _, year1, month1, day1 ->
            val month2: Int = month1 + 1
            date = "$day1 - $month2 - $year1"
            viewModel.fecha = Date(year1, month2, day1)
            invalidDate = dateInvalid(viewModel.fecha)
        }, year, month, day
    )

    LaunchedEffect(Unit) {
        viewModel.isMessageShownFlow.collectLatest {
            if (it) {
                snackbarHostState.showSnackbar(
                    message = "Gasto guardado",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(8.dp)
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Text(text = "Gastos detalles", style = MaterialTheme.typography.titleMedium)

            CustomOutlinedTextField(
                value = viewModel.suplidor,
                onValueChange = { viewModel.suplidor = it },
                label = "Suplidor",
                isError = viewModel.isValidSuplidor,
                imeAction = ImeAction.Next
            )
            CustomOutlinedTextField(
                value = viewModel.ncf,
                onValueChange = { viewModel.ncf = it },
                label = "NCF",
                isError = viewModel.isValidNcf,
                imeAction = ImeAction.Next
            )
            CustomOutlinedTextField(
                value = viewModel.concepto,
                onValueChange = { viewModel.concepto = it },
                label = "Concepto",
                isError = viewModel.isValidConcepto,
                imeAction = ImeAction.Next
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.descuento.toString(),
                label = { Text(text = "Descuento") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.descuento = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.itbis.toString(),
                label = { Text(text = "Itbis") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.itbis = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.monto.toString(),
                label = { Text(text = "Monto") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.monto = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Fecha")},
                value = date,
                onValueChange = {},
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                datePickerDialog.show()
                            }
                            .size(30.dp, 30.dp)
                    )
                },
            )

            OutlinedButton(onClick = {
                keyboardController?.hide()
                if (viewModel.isValid()) {
                    viewModel.saveGasto()
                    viewModel.setMessageShown()
                }
            }, modifier = Modifier.fillMaxWidth())

            {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Save")
                Text(text = "Save")
            }

            uiState.gastos?.let { gastos ->
                Consult(gastos)
            }
        }
    }
}


@Composable
fun Consult(gastos: List<GastosDto>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(gastos) { gasto ->
                GastosItem(gasto)
            }
        }
    }
}

@Composable
fun GastosItem(gastos: GastosDto) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize()
    ){
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "ID: " + gastos.idGasto, style = MaterialTheme.typography.titleMedium)
            Text(text = "" + gastos.fecha, style = MaterialTheme.typography.titleMedium)
            Text(text = gastos.suplidor, style = MaterialTheme.typography.titleMedium)
            Text(text = gastos.concepto, style = MaterialTheme.typography.titleMedium)
            Text(text = "NCF: " + gastos.ncf, style = MaterialTheme.typography.titleMedium)
            Text(text = "Itbis: " + gastos.itbis, style = MaterialTheme.typography.titleMedium)
            Text(text = "Monto: " + gastos.monto, style = MaterialTheme.typography.titleMedium)

            Divider(modifier = Modifier.fillMaxWidth(), color = Color.Gray, thickness = 1.dp)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    imeAction: ImeAction
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if (isError) Color.Gray else Color.Red,
            unfocusedBorderColor = if (isError) Color.Gray else Color.Red
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction)
    )
}

@Suppress("DEPRECATION")
private fun dateInvalid(date: Date): Boolean {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR) - 17
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    return date > Date(year, month, day) || date < Date(1930,1,1)
}