package br.com.fiap.ecotrace.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrace.components.AddEmissionDialog
import br.com.fiap.ecotrace.components.BottomNavBar
import br.com.fiap.ecotrace.components.EmissionRecord
import br.com.fiap.ecotrace.model.EmissionCategory
import com.ecotrace.navigation.Destination


@Composable
fun RegisterScreen(onNavigate: (String) -> Unit,
                   records: List<EmissionRecord>,
                   onAddRecord: (EmissionRecord) -> Unit,
                   ) {

    var records by remember { mutableStateOf(listOf<EmissionRecord>()) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = Destination.RegisterScreen.route,
                onNavigate = onNavigate
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Adicionar registro",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {
            ResumeSummaryCards(records = records)

            // Histórico de registros
            if (records.isEmpty()) {
                // Estado vazio — importante para UX
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum registro ainda.\nToque em + para começar!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(records) { record ->
                        RecordItem(record = record)
                    }
                }
            }
        }

        if (showDialog) {
            AddEmissionDialog(
                onDismiss = { showDialog = false },
                onConfirm = { newRecord ->
                    // Adiciona o novo registro à lista existente
                    records = records + newRecord
                }
            )
        }
    }
}

@Composable
private fun ResumeSummaryCards(records: List<EmissionRecord>) {

    val transportTotal = records
        .filter { it.category == EmissionCategory.TRANSPORT }
        .sumOf { it.quantity.toDouble() }
        .toFloat()

    val foodTotal = records
        .filter { it.category == EmissionCategory.FOOD }
        .sumOf { it.quantity.toDouble() }
        .toFloat()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            title = "Transporte",
            total = transportTotal,
            unit = "km",
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            title = "Alimentação",
            total = foodTotal,
            unit = "g",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SummaryCard(
    title: String,
    total: Float,
    unit: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "%.1f %s".format(total, unit),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun RecordItem(record: EmissionRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Column {
                Text(
                    text = when (record.category) {
                        EmissionCategory.TRANSPORT -> "Transporte"
                        EmissionCategory.FOOD      -> "Alimento"
                        else -> "Categoria inexistente"
                    },
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Tipo: ${record.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = when (record.category) {
                        EmissionCategory.TRANSPORT -> "Distância: ${record.quantity}km"
                        EmissionCategory.FOOD      -> "Quantidade: ${record.quantity}g"
                        else -> "Categoria inexistente"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}