package com.ecotrace.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrace.components.EcoTextField
import com.ecotrace.ui.theme.EcoTraceTheme

@Composable
fun LoginScreen(
    // NavController seria passado aqui para navegar
    // Por enquanto, usamos lambdas simples para isolar a lógica
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    // Estado local da tela
    // Toda vez que o usuário digita, o Compose re-executa
    // essa função e atualiza a UI automaticamente
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Box ocupa a tela toda com fundo EcoBackground
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            // Título da tela
            Text(
                text = "Entre com sua conta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            // Card cinza que envolve os campos — conforme seu design
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // Reutilizando o componente que criamos
                    EcoTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "email",
                        leadingIcon = Icons.Default.Email,

                    )

                    EcoTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = "Senha",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )

                    // Texto clicável "Não tem uma conta? crie aqui"
                    // AnnotatedString permite estilizar partes do texto
                    TextButton(
                        onClick = onSignupClick,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Não tem uma conta? ")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("crie aqui")
                                }
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Botão principal de Login
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun LoginScreenPreview() {
    EcoTraceTheme() {
        LoginScreen(
            onLoginClick = {},
            onSignupClick = {}
        )
    }

}