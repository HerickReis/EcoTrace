package br.com.fiap.ecotrace.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    // Os itens recebem a string de rota como dado,
    // não como referência ao Destination
    val items = listOf(

        NavItem(route = "profile_screen",   icon = Icons.Default.Person,    label = "Perfil"),
        NavItem(route = "dashboard_screen", icon = Icons.Default.Home,      label = "Início"),
        NavItem(route = "register_screen",  icon = Icons.Default.List,      label = "Registros"),
        NavItem(route = "tips_screen",      icon = Icons.Default.Lightbulb, label = "Dicas"),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onNavigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSecondary,
                    selectedTextColor = MaterialTheme.colorScheme.onSecondary,

                    // Ícone e texto quando NÃO está selecionado
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,

                    indicatorColor = MaterialTheme.colorScheme.secondary)
            )
        }
    }
}


@Preview
@Composable
private fun BottomNavBarPreview() {
    BottomNavBar(
        currentRoute = "dashboard_screen",
        onNavigate = {}
    )

}