package br.com.fiap.ecotrace.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.fiap.ecotrace.components.EmissionRecord
import br.com.fiap.ecotrace.data.database.EcoTraceDatabase
import br.com.fiap.ecotrace.data.entity.EmissionRecordEntity
import br.com.fiap.ecotrace.model.EmissionCategory
import br.com.fiap.ecotrace.screens.DashboardScreen
import br.com.fiap.ecotrace.screens.InitialScreen
import br.com.fiap.ecotrace.screens.ProfileScreen
import br.com.fiap.ecotrace.screens.RegisterScreen
import br.com.fiap.ecotrace.screens.SignupScreen
import br.com.fiap.ecotrace.screens.TipsScreen
import com.ecotrace.navigation.Destination
import com.ecotrace.ui.screens.LoginScreen
import kotlinx.coroutines.launch
import br.com.fiap.ecotrace.data.session.SessionManager

@Composable
fun AppNavGraph(navController: NavHostController, context: Context) {

    val database       = remember { EcoTraceDatabase.getDatabase(context) }
    val dao            = remember { database.emissionRecordDao() }
    val userDao        = remember { database.userDao() }
    val sessionManager = remember { SessionManager(context) }

    var loggedUserName by remember { mutableStateOf(sessionManager.getLoggedUserName()) }


    val recordEntities by dao.getAllRecords().collectAsState(initial = emptyList())

    val records = remember(recordEntities) {
        recordEntities.map { entity ->
            EmissionRecord(
                category = EmissionCategory.valueOf(entity.category),
                type = entity.type,
                quantity = entity.quantity,
                cokg = entity.coKg
            )
        }
    }

    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination =
        if (sessionManager.isLoggedIn())
            Destination.DashboardScreen.route  // já logado — pula login
        else
            Destination.InitialScreen.route
    ) {
        composable(Destination.InitialScreen.route) {
            InitialScreen(
                onLoginClick  = { navController.navigate(Destination.LoginScreen.route) },
                onSignupClick = { navController.navigate(Destination.SignupScreen.route) }
            )
        }

        composable(Destination.LoginScreen.route) {
            LoginScreen(
                userDao        = userDao,
                sessionManager = sessionManager,
                onLoginClick   = {
                    loggedUserName = sessionManager.getLoggedUserName()
                    navController.navigate(Destination.DashboardScreen.route) {
                        // Remove Login e Initial da pilha — botão voltar não retorna para elas
                        popUpTo(Destination.InitialScreen.route) { inclusive = true }
                    }
                },
                onSignupClick  = { navController.navigate(Destination.SignupScreen.route) }
            )
        }

        composable(Destination.SignupScreen.route) {
            SignupScreen(
                userDao              = userDao,
                sessionManager       = sessionManager,
                onCreateAccountClick = {
                    loggedUserName = sessionManager.getLoggedUserName()
                    navController.navigate(Destination.DashboardScreen.route) {
                        popUpTo(Destination.InitialScreen.route) { inclusive = true }
                    }
                },
                onLoginClick = { navController.navigate(Destination.LoginScreen.route) }
            )
        }

        composable(Destination.DashboardScreen.route) {
            DashboardScreen(
                records      = records,
                userName     = loggedUserName,
                onNavigate   = { navController.navigate(it) }
            )
        }

        composable(Destination.RegisterScreen.route) {
            RegisterScreen(
                records     = records,
                onAddRecord = { newRecord ->
                    scope.launch {
                        dao.insertRecord(
                            EmissionRecordEntity(
                                category = newRecord.category.name,
                                type     = newRecord.type,
                                quantity = newRecord.quantity,
                                coKg     = newRecord.cokg
                            )
                        )
                    }
                },
                onNavigate = { navController.navigate(it) }
            )
        }

        composable(Destination.TipsScreen.route) {
            TipsScreen(
                records    = records,
                onNavigate = { navController.navigate(it) }
            )
        }

        composable(Destination.ProfileScreen.route) {
            ProfileScreen(
                records    = records,
                onNavigate = { navController.navigate(it) },
                userName = loggedUserName
            )
        }
    }
}