package br.com.fiap.ecotrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import br.com.fiap.ecotrace.navigation.AppNavGraph
import com.ecotrace.ui.theme.EcoTraceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoTraceTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}