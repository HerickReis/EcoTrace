package br.com.fiap.ecotrace.components

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import br.com.fiap.ecotrace.model.EmissionData
import br.com.fiap.ecotrace.model.MockEmissionData
import com.ecotrace.ui.theme.EcoTraceTheme
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

@Composable
fun DonutChartCard(
    emissionData: EmissionData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Título do card
            Text(
                text = emissionData.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // AndroidView — aqui mora o gráfico MPAndroidChart
            // O Compose reserva 220dp de altura e entrega para a View
            AndroidView(
                factory = { context ->
                    // factory roda UMA vez — cria e configura o gráfico
                    PieChart(context).apply {
                        // Aparência geral
                        description.isEnabled = false   // remove label "Description"
                        isDrawHoleEnabled = true         // cria o buraco do donut
                        holeRadius = 40f                 // tamanho do buraco (%)
                        transparentCircleRadius = 40f    // anel suave ao redor
                        setDrawCenterText(true)
                        centerText = "CO₂"
                        setCenterTextSize(16f)
                        setCenterTextTypeface(Typeface.DEFAULT_BOLD)

                        // Remove a legenda de canto - legenda personalizada
                        legend.isEnabled = false

                        // Remove animação de toque
                        isRotationEnabled = false
                        setTouchEnabled(false)
                    }
                },
                update = { pieChart ->
                    // atualiza a  roda quando os dados mudam
                    // Converte EmissionSlice para PieEntry (formato do MPAndroidChart)
                    val entries = emissionData.slices.map { slice ->
                        PieEntry(slice.valueKg, slice.label)
                    }

                    val colors = emissionData.slices.map { it.color }

                    val dataSet = PieDataSet(entries, "").apply {
                        this.colors = colors
                        sliceSpace = 2f          // espaço entre fatias
                        valueTextSize = 11f
                        valueTextColor = android.graphics.Color.WHITE
                    }

                    pieChart.data = PieData(dataSet)
                    pieChart.invalidate()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            // legenda com dados
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                emissionData.slices.forEach { slice ->
                    LegendItem(
                        label = slice.label,
                        percentage = "${((slice.valueKg / emissionData.totalKg) * 100).toInt()}%",
                        color = androidx.compose.ui.graphics.Color(slice.color)
                    )
                }
            }

            Text(
                text = emissionData.equivalenceText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}

// Componente auxiliar para cada item da legenda
@Composable
private fun LegendItem(
    label: String,
    percentage: String,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, shape = MaterialTheme.shapes.extraSmall)
        )
        Text(
            text = "$label $percentage",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DonutChartCardPreview() {
    EcoTraceTheme {
        DonutChartCard(
            emissionData = MockEmissionData.food,
            modifier = Modifier.padding(16.dp)
        )
    }
}