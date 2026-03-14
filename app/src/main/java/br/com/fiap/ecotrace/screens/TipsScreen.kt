package br.com.fiap.ecotrace.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Restaurant
import br.com.fiap.ecotrace.components.BottomNavBar
import com.ecotrace.navigation.Destination
import br.com.fiap.ecotrace.components.EmissionRecord
import br.com.fiap.ecotrace.model.EmissionCategory
import br.com.fiap.ecotrace.model.TipGenerator

@Composable
fun TipsScreen(
    records: List<EmissionRecord>,
    onNavigate: (String) -> Unit
) {
    // remember(records) — recalcula as dicas só quando
    // os registros mudarem, não a cada recomposição
    val tips = remember(records) { TipGenerator.generate(records) }

    // PagerState controla qual página do carrossel está visível
    // pageCount define quantas páginas existem — usamos as 3 primeiras
    // dicas como banner (as mais relevantes já estão no topo da lista)
    val pagerState = rememberPagerState(pageCount = { minOf(tips.size, 3) })

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentRoute = Destination.TipsScreen.route,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            // Banner carrossel
            item {
                BannerCarousel(
                    tips = tips.take(3),
                    pagerState = pagerState
                )
            }

            // Separador com título
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = "  Educação ambiental  ",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }
            }

            // Cards educativos — todas as dicas em ordem de prioridade
            items(tips) { tip ->
                TipCard(
                    tip = tip,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }
    }
}

// ── Banner Carrossel ──────────────────────────────────────

@Composable
private fun BannerCarousel(
    tips: List<br.com.fiap.ecotrace.model.Tip>,
    pagerState: androidx.compose.foundation.pager.PagerState
) {
    Column {
        // HorizontalPager é o componente de carrossel do Compose
        // Cada "página" é um bloco dentro do lambda
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { pageIndex ->
            BannerPage(tip = tips[pageIndex])
        }

        // Indicadores de página (pontinhos)
        // pagerState.currentPage diz qual página está ativa
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (isSelected) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                )
            }
        }
    }
}

@Composable
private fun BannerPage(tip: br.com.fiap.ecotrace.model.Tip) {
    // Box empilha o gradiente sobre o fundo colorido
    // simulando o efeito de imagem com texto sobreposto do seu Figma
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Fundo — gradiente verde simulando a foto de floresta
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primary
                        )
                    )
                )
        )

        // Texto sobre o banner
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text(
                text = tip.title,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                fontSize = 20.sp
            )
        }
    }
}

// ── Card educativo ────────────────────────────────────────

@Composable
private fun TipCard(
    tip: br.com.fiap.ecotrace.model.Tip,
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
            verticalAlignment = Alignment.Top
        ) {
            // Ícone verde quadrado — igual ao seu Figma
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = when (tip.category) {
                            EmissionCategory.TRANSPORT ->
                                androidx.compose.material.icons.Icons.Default.DirectionsCar
                            EmissionCategory.FOOD ->
                                androidx.compose.material.icons.Icons.Default.Restaurant
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = tip.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}