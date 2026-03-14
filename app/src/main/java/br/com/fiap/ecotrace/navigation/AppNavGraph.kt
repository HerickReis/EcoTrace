package br.com.fiap.ecotrace.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecotrace.components.EmissionRecord
import br.com.fiap.ecotrace.screens.DashboardScreen
import br.com.fiap.ecotrace.screens.InitialScreen
import br.com.fiap.ecotrace.screens.ProfileScreen
import br.com.fiap.ecotrace.screens.RegisterScreen
import br.com.fiap.ecotrace.screens.SignupScreen
import br.com.fiap.ecotrace.screens.TipsScreen
import com.ecotrace.navigation.Destination
import com.ecotrace.ui.screens.LoginScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    // O remember fica AQUI DENTRO — antes do NavHost
    // Assim ele pertence ao AppNavGraph e todas as rotas
    // dentro do NavHost conseguem acessar a mesma lista
    var records by remember { mutableStateOf(listOf<EmissionRecord>()) }

    NavHost(
        navController = navController,
        startDestination = Destination.InitialScreen.route
    ) {

        composable(Destination.InitialScreen.route) {
            InitialScreen(
                onLoginClick  = { navController.navigate(Destination.LoginScreen.route) },
                onSignupClick = { navController.navigate(Destination.SignupScreen.route) }
            )
        }

        composable(Destination.LoginScreen.route) {
            LoginScreen(
                onLoginClick  = { navController.navigate(Destination.DashboardScreen.route) },
                onSignupClick = { navController.navigate(Destination.SignupScreen.route) }
            )
        }

        composable(Destination.SignupScreen.route) {
            SignupScreen(
                onCreateAccountClick = { navController.navigate(Destination.DashboardScreen.route) },
                onLoginClick         = { navController.navigate(Destination.LoginScreen.route) }
            )
        }

        composable(Destination.DashboardScreen.route) {
            DashboardScreen(
                onNavigate = { navController.navigate(it) }
            )
        }

        // RegisterScreen recebe a lista E um callback para adicionar
        composable(Destination.RegisterScreen.route) {
            RegisterScreen(
                records = records,
                onAddRecord = { records = records + it },
                onNavigate = { navController.navigate(it) }
            )
        }

        // ProfileScreen recebe a mesma lista — dados sempre sincronizados
        composable(Destination.ProfileScreen.route) {
            ProfileScreen(
                records = records,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable(Destination.TipsScreen.route) {
            TipsScreen(
                records = records,
                onNavigate = { navController.navigate(it) }
            )
        }


    }
}
