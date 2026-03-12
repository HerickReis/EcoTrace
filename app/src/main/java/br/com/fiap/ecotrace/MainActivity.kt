package br.com.fiap.ecotrace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.fiap.ecotrace.screens.InitialScreen
import com.ecotrace.ui.theme.EcoTraceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EcoTraceTheme {
                InitialScreen()
            }
        }
    }
}