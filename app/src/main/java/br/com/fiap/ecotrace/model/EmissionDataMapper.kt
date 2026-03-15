package br.com.fiap.ecotrace.model

import android.graphics.Color
import br.com.fiap.ecotrace.components.EmissionRecord

object EmissionDataMapper {
    private val transportColors = mapOf(
        "Carro"     to Color.parseColor("#AB47BC"),
        "Ônibus"    to Color.parseColor("#FFCA28"),
        "Bicicleta" to Color.parseColor("#4CAF50"),
        "A pé"      to Color.parseColor("#66BB6A"),
    )

    private val foodColors = mapOf(
        "Carne"      to Color.parseColor("#AB47BC"),
        "Frango"     to Color.parseColor("#FF7043"),
        "Laticínios" to Color.parseColor("#FFCA28"),
        "Vegetais"   to Color.parseColor("#4CAF50"),
    )

    fun toTransportData(records: List<EmissionRecord>): EmissionData {
        val filtered = records.filter { it.category == EmissionCategory.TRANSPORT }
        return buildEmissionData(
            title = "Emissão CO₂ por tipo de transporte",
            records = filtered,
            colorMap = transportColors
        )
    }

    fun toFoodData(records: List<EmissionRecord>): EmissionData {
        val filtered = records.filter { it.category == EmissionCategory.FOOD }
        return buildEmissionData(
            title = "Emissão CO₂ por tipo de alimento",
            records = filtered,
            colorMap = foodColors
        )
    }

    private fun buildEmissionData(
        title: String,
        records: List<EmissionRecord>,
        colorMap: Map<String, Int>
    ): EmissionData {

        val grouped = records
            .groupBy { it.type }
            .mapValues { (_, group) ->
                group.sumOf { it.cokg.toDouble() }.toFloat()
            }

        // Transforma o Map em List<EmissionSlice>
        val slices = grouped.map { (type, totalCo2) ->
            EmissionSlice(
                label = type,
                valueKg = totalCo2,
                color = colorMap[type] ?: Color.GRAY
            )
        }

        val totalKg = slices.sumOf { it.valueKg.toDouble() }.toFloat()

        val equivalenceText = if (totalKg == 0f) {
            "Nenhuma emissão registrada ainda."
        } else {
            val arvores = (totalKg / 10).toInt()
            "Total de emissão: %.1fkg/mês — equivale a %d árvores jovens.".format(totalKg, arvores)
        }

        return EmissionData(
            title = title,
            slices = slices,
            totalKg = totalKg,
            equivalenceText = equivalenceText
        )
    }
}