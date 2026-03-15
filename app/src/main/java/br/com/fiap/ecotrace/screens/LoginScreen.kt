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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrace.components.EcoTextField
import br.com.fiap.ecotrace.data.dao.UserDao
import br.com.fiap.ecotrace.data.session.SessionManager
import br.com.fiap.ecotrace.model.AuthValidator
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
    userDao: UserDao,
    sessionManager: SessionManager,
    onLoginClick: () -> Unit,
    onSignupClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var generalError by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

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
            Text(
                text = "Entre com sua conta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                shape = MaterialTheme.shapes.large
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    EcoTextField(
                        value = email,
                        onValueChange = { email = it; emailError = null },
                        label = "E-mail",
                        leadingIcon = Icons.Default.Email
                    )
                    emailError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    EcoTextField(
                        value = senha,
                        onValueChange = { senha = it; passwordError = null },
                        label = "Senha",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )
                    passwordError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    generalError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium)
                    }

                    TextButton(
                        onClick = onSignupClick,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Não tem uma conta? ")
                                withStyle(SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )) { append("crie aqui") }
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val emailVal = AuthValidator.validateEmail(email)
                            val passVal  = AuthValidator.validatePassword(senha)

                            emailError    = if (!emailVal.isValid) emailVal.errorMessage else null
                            passwordError = if (!passVal.isValid) passVal.errorMessage else null

                            if (emailVal.isValid && passVal.isValid) {
                                scope.launch {
                                    val user = userDao.getUserByEmail(email.trim().lowercase())

                                    when {
                                        user == null ->
                                            generalError = "E-mail não cadastrado"
                                        user.password != senha ->
                                            generalError = "Senha incorreta"
                                        else -> {
                                            sessionManager.saveSession(user.id, user.name)
                                            onLoginClick()
                                        }
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text("Login", style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            }
        }
    }
}