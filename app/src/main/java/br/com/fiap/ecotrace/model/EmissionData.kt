package br.com.fiap.ecotrace.model

data class EmissionSlice(
    val label: String,      // "Carro", "Ônibus", "Caminhada"
    val valueKg: Float,     // 130f, 149f, 93f
    val color: Int          // Cor no formato Android (Int) — usaremos android.graphics.Color
)

data class EmissionData(
    val title: String,              // "Emissão CO₂ por tipo de transporte"
    val slices: List<EmissionSlice>,
    val totalKg: Float,             // 372f
    val equivalenceText: String     // "isso equivale a 37 árvores jovens"
)

object MockEmissionData {

    val transport = EmissionData(
        title = "Emissão CO₂ por tipo de transporte",
        slices = listOf(
            EmissionSlice("Ônibus",    valueKg = 149f, color = android.graphics.Color.parseColor("#FFCA28")),
            EmissionSlice("Carro",     valueKg = 130f, color = android.graphics.Color.parseColor("#AB47BC")),
            EmissionSlice("Caminhada", valueKg = 93f,  color = android.graphics.Color.parseColor("#4CAF50")),
        ),
        totalKg = 372f,
        equivalenceText = "Total de emissão, 372kg/mês, isso equivale ao consumo de 37 árvores jovens, e representa uma redução de 12% na emissão."
    )

    val food = EmissionData(
        title = "Emissão CO₂ por tipo de alimento",
        slices = listOf(
            EmissionSlice("Laticínios", valueKg = 292f, color = android.graphics.Color.parseColor("#FFCA28")),
            EmissionSlice("Carne",      valueKg = 255f, color = android.graphics.Color.parseColor("#AB47BC")),
            EmissionSlice("Vegetais",   valueKg = 183f, color = android.graphics.Color.parseColor("#4CAF50")),
        ),
        totalKg = 730f,
        equivalenceText = "Total de emissão, 730kg/mês, isso equivale ao consumo energético de 25 residências, e representa um aumento de 32% na emissão."
    )
}