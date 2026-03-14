package br.com.fiap.ecotrace.model

data class Tip(
    val title: String,          // "Troque o carro pelo ônibus"
    val description: String,    // texto educativo completo
    val category: EmissionCategory,  // de qual categoria vem a dica
    val priority: Int           // quanto menor, mais relevante pro usuário
)