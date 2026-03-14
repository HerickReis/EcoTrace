package br.com.fiap.ecotrace.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrace.components.BottomNavBar
import br.com.fiap.ecotrace.components.EmissionRecord
import br.com.fiap.ecotrace.model.Achievement
import br.com.fiap.ecotrace.model.AchievementChecker
import br.com.fiap.ecotrace.model.EmissionCategory
import com.ecotrace.navigation.Destination

@Composable
fun ProfileScreen(
    records: List<EmissionRecord>,  // vem de fora — mesma lista do RegisterScreen
    onNavigate: (String) -> Unit
) {
    // remember(records) recalcula as conquistas SOMENTE quando
    // a lista de registros mudar — não a cada recomposição
    // Isso é diferente de remember sem parâmetro
    val achievements = remember(records) {
        AchievementChecker.check(records)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = Destination.ProfileScreen.route,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {

            // Header com avatar e nome
            item {
                ProfileHeader()
            }

            // Seção de conquistas
            item {
                Text(
                    text = "Conquistas",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )

                // LazyRow — lista horizontal que só renderiza
                // o que está visível, igual ao LazyColumn mas na horizontal
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(achievements) { achievement ->
                        AchievementBadge(achievement = achievement)
                    }
                }
            }

            // Seção de histórico
            item {
                Text(
                    text = "Últimas informações adicionadas",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }

            if (records.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nenhum registro ainda.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            } else {
                items(records.takeLast(5).reversed()) { record ->
                    RecordHistoryItem(
                        record = record,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// ── Header ────────────────────────────────────────────────

@Composable
private fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Avatar",
            modifier = Modifier.size(48.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Olá, Letycia",
            style = MaterialTheme.typography.headlineMedium
        )
    }

    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
}

// ── Badge de conquista ────────────────────────────────────

@Composable
private fun AchievementBadge(achievement: Achievement) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        // alpha() controla a opacidade —
        // conquistas bloqueadas ficam transparentes (apagadas)
        // exatamente como no seu Figma
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = if (achievement.isUnlocked)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier
                .size(56.dp)
                .alpha(if (achievement.isUnlocked) 1f else 0.4f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = achievement.icon,
                    contentDescription = achievement.name,
                    tint = if (achievement.isUnlocked)
                        MaterialTheme.colorScheme.onPrimary
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Text(
            text = achievement.name,
            style = MaterialTheme.typography.labelMedium,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier
                .padding(top = 4.dp)
                .alpha(if (achievement.isUnlocked) 1f else 0.4f)
        )
    }
}

// ── Item do histórico ─────────────────────────────────────

@Composable
private fun RecordHistoryItem(
    record: EmissionRecord,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
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
                imageVector = when (record.category) {
                    EmissionCategory.TRANSPORT -> Icons.Default.DirectionsCar
                    EmissionCategory.FOOD      -> Icons.Default.Restaurant
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = when (record.category) {
                        EmissionCategory.TRANSPORT -> "Transporte"
                        EmissionCategory.FOOD      -> "Alimento"
                    },
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Tipo: ${record.type}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            // CO₂ destacado à direita
            Text(
                text = "%.1f kg".format(record.cokg),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}