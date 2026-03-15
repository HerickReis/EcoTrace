package br.com.fiap.ecotrace.model

import br.com.fiap.ecotrace.components.EmissionRecord

object TipGenerator {
    fun generate(records: List<EmissionRecord>): List<Tip> {

        // Analisa o comportamento do usuário
        val carUsage = records.count {
            it.category == EmissionCategory.TRANSPORT && it.type == "Carro"
        }
        val meatUsage = records.count {
            it.category == EmissionCategory.FOOD && it.type == "Carne"
        }
        val bikeUsage = records.count {
            it.category == EmissionCategory.TRANSPORT && it.type == "Bicicleta"
        }

        val tips = mutableListOf<Tip>()

        tips.add(Tip(
            title = "Troque o carro pelo transporte público",
            description = "O ônibus emite até 60% menos CO₂ por passageiro do que o carro particular. Uma simples mudança de hábito pode reduzir sua pegada significativamente.",
            category = EmissionCategory.TRANSPORT,
            priority = if (carUsage >= 3) 1 else 5
        ))

        tips.add(Tip(
            title = "Uma refeição vegetariana por semana",
            description = "Substituir carne bovina por vegetais uma vez por semana pode reduzir sua emissão alimentar em até 15% ao mês. Pequenas escolhas, grande impacto.",
            category = EmissionCategory.FOOD,
            priority = if (meatUsage >= 3) 1 else 4
        ))

        tips.add(Tip(
            title = "Parabéns por usar a bicicleta!",
            description = "Ciclistas urbanos evitam em média 150kg de CO₂ por ano. Continue assim — você está no caminho certo.",
            category = EmissionCategory.TRANSPORT,
            priority = if (bikeUsage >= 1) 2 else 8
        ))

        tips.add(Tip(
            title = "O impacto da carne bovina no clima",
            description = "A produção de 1kg de carne bovina gera 27kg de CO₂ — o equivalente a dirigir 130km. Frango e vegetais têm pegada até 10x menor.",
            category = EmissionCategory.FOOD,
            priority = if (meatUsage >= 2) 2 else 6
        ))

        tips.add(Tip(
            title = "Compensação de carbono",
            description = "Além de reduzir emissões, você pode compensar o CO₂ inevitável apoiando projetos de reflorestamento. Muitas empresas brasileiras oferecem essa opção.",
            category = EmissionCategory.TRANSPORT,
            priority = 7
        ))

        tips.add(Tip(
            title = "Carona solidária reduz emissões",
            description = "Compartilhar o carro com colegas divide as emissões pelo número de passageiros. Quatro pessoas num carro emitem o mesmo que uma no ônibus.",
            category = EmissionCategory.TRANSPORT,
            priority = if (carUsage >= 2) 3 else 7
        ))

        return tips.sortedBy { it.priority }
    }
}