package com.kotlin.albert_p2_ap2.ui.gastos

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kotlin.albert_p2_ap2.data.remote.dto.GastosDto
import com.kotlin.albert_p2_ap2.ui.theme.Green70
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Register(viewModel: GastosViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val context = LocalContext.current
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)
    var invalidDate by remember { mutableStateOf(true) }

    var date by remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedDate = android.icu.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(Date(year - 1900, month, dayOfMonth))
            viewModel.fecha = formattedDate
        },
        year,
        month,
        day
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

            Text(
                text = "Registro de gastos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = viewModel.idSuplidor.toString(),
                label = { Text(text = "IdSuplidor") },
                singleLine = true,
                onValueChange = {
                    val newValue = it.toIntOrNull()
                    if (newValue != null) {
                        viewModel.idSuplidor = newValue
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                )
            )
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
                value = viewModel.fecha,
                onValueChange = { viewModel.fecha = it },
                enabled = false,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        tint = Color.Black,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                datePickerDialog.show()
                            }
                            .size(30.dp, 30.dp)
                    )
                },
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Default
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color.Blue,
                )
            )

            OutlinedButton(
                onClick = {
                    keyboardController?.hide()
                    if (viewModel.isValid()) {
                        viewModel.saveGasto()
                        viewModel.setMessageShown()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Guardar")
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }


            uiState.gastos?.let { gasto -> Consult(gasto) }
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
fun GastosItem(gastos: GastosDto, viewModel: GastosViewModel = hiltViewModel()) {

    val fechaParseada = LocalDateTime.parse(gastos.fecha, DateTimeFormatter.ISO_DATE_TIME)
    val fechaFormateada = fechaParseada.format(DateTimeFormatter.ISO_DATE)
    OutlinedCard(modifier = Modifier.padding(6.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier=Modifier.weight(1f)) {
                    Text(text = "ID: ${gastos.idGasto}")
                }
                Column(modifier=Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(text = fechaFormateada)
                }
            }
            Row (modifier = Modifier.fillMaxWidth())
            {
                gastos.suplidor?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            gastos.concepto?.let {
                Text(
                    text = it,
                    maxLines=2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.padding(top = 4.dp))
            Row (modifier = Modifier.fillMaxWidth()){
                Column(modifier=Modifier.weight(1f)) {
                    Text(text = "NCF: ${gastos.ncf}")
                    Text(text = "ITBIS: RD$" +gastos.itbis.toString())
                }
                Row (
                    horizontalArrangement = Arrangement.End,
                    modifier= Modifier.weight(1f)
                ){
                    Text(
                        text = "RD$$"+ gastos.monto.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
            Divider()
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ElevatedButton(onClick = {

                }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            tint = Green70
                        )
                        Text(
                            text = "Editar",
                            modifier = Modifier.padding(top = 3.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(end = 20.dp))
                OutlinedButton(
                    onClick = {
                        gastos.idGasto?.let { viewModel.deleteGastos(it)

                        } }) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Color.Red
                        )
                        Text(
                            text = "Eliminar",
                            modifier = Modifier.padding(top = 3.dp),
                        )
                    }
                }
            }
        }
    }
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

private fun dateInvalid(date: String): Boolean {
    val currentDate = SimpleDateFormat("dd - MM - yyyy", Locale.getDefault()).format(Date())
    val eighteenYearsAgo = SimpleDateFormat("dd - MM - yyyy", Locale.getDefault()).format(
        Calendar.getInstance().apply {
            add(Calendar.YEAR, -17)
        }.time
    )

    return date > currentDate || date < "01 - 01 - 1930" || date > eighteenYearsAgo
}
