package br.com.fiap.ecotrace.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Shield
import androidx.compose.ui.graphics.vector.ImageVector
import br.com.fiap.ecotrace.components.EmissionRecord

data class Achievement(
    val name: String,
    val icon: ImageVector,
    val description: String,
    val isUnlocked: Boolean
)
object AchievementChecker {
    fun check(records: List<EmissionRecord>): List<Achievement> {

        val bikeCount = records.count {
            it.category == EmissionCategory.TRANSPORT && it.type == "Bicicleta"
        }

        val veggieCount = records.count {
            it.category == EmissionCategory.FOOD && it.type == "Vegetais"
        }

        val totalCo2 = records.sumOf { it.cokg.toDouble() }.toFloat()

        return listOf(
            Achievement(
                name = "Ciclista Nato",
                icon = Icons.Default.DirectionsBike,
                description = "Registre bicicleta 3 vezes",
                isUnlocked = bikeCount >= 3
            ),
            Achievement(
                name = "Vegetariano",
                icon = Icons.Default.Eco,
                description = "Registre vegetais 5 vezes",
                isUnlocked = veggieCount >= 5
            ),
            Achievement(
                name = "Ambientalista",
                icon = Icons.Default.Park,
                description = "Mantenha CO₂ abaixo de 100kg",
                isUnlocked = totalCo2 < 100f && records.isNotEmpty()
            ),
            Achievement(
                name = "Explorador",
                icon = Icons.Default.Explore,
                description = "Faça seu primeiro registro",
                isUnlocked = records.isNotEmpty()
            ),
            Achievement(
                name = "Eco Warrior",
                icon = Icons.Default.Shield,
                description = "Registre 10 atividades",
                isUnlocked = records.size >= 10
            )
        )
    }
}