package br.com.fiap.ecotrace.model

// Fatores baseados em dados do IPCC e SEEG Brasil
// Unidades:
//   Transporte → kg CO₂ por km
//   Alimento   → kg CO₂ por kg consumido

object EmissionFactors {

    val transport = mapOf(
        "Carro"      to 0.21f,   // gasolina, veículo médio
        "Ônibus"     to 0.089f,  // transporte coletivo urbano
        "Bicicleta"  to 0.0f,    // emissão zero
        "A pé"       to 0.0f
    )

    val food = mapOf(
        "Carne"      to 27.0f,   // kg CO₂ por kg de carne bovina
        "Frango"     to 6.9f,
        "Laticínios" to 3.2f,
        "Vegetais"   to 2.0f
    )

    // Calcula o CO₂ dado um tipo e quantidade
    // Retorna 0 se o tipo não for encontrado
    fun calculate(category: EmissionCategory, type: String, quantity: Float): Float {
        return when (category) {
            EmissionCategory.TRANSPORT -> {
                val factor = transport[type] ?: 0f
                factor * quantity                    // fator * km
            }
            EmissionCategory.FOOD -> {
                val factor = food[type] ?: 0f
                factor * (quantity / 1000f)          // converte g → kg antes de multiplicar
            }
        }
    }
}