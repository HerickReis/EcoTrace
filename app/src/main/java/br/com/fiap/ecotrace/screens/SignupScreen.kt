package br.com.fiap.ecotrace.screens

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
import androidx.compose.material.icons.filled.Person
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
import br.com.fiap.ecotrace.data.entity.UserEntity
import br.com.fiap.ecotrace.data.session.SessionManager
import br.com.fiap.ecotrace.model.AuthValidator
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(
    userDao: UserDao,
    sessionManager: SessionManager,
    onCreateAccountClick: () -> Unit,
    onLoginClick: () -> Unit
) {
    var name         by remember { mutableStateOf("") }
    var email        by remember { mutableStateOf("") }
    var senha        by remember { mutableStateOf("") }
    var confirmaSenha by remember { mutableStateOf("") }

    var nameError     by remember { mutableStateOf<String?>(null) }
    var emailError    by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmError  by remember { mutableStateOf<String?>(null) }
    var generalError  by remember { mutableStateOf<String?>(null) }

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
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    EcoTextField(
                        value = name,
                        onValueChange = { name = it; nameError = null },
                        label = "Nome",
                        leadingIcon = Icons.Default.Person
                    )

                    nameError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    EcoTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "email",
                        leadingIcon = Icons.Default.Email
                    )

                    emailError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    EcoTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = "Senha",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )

                    passwordError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    EcoTextField(
                        value = confirmaSenha,
                        onValueChange = { confirmaSenha = it },
                        label = "Confirme a senha",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true
                    )

                    confirmError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.labelMedium)
                    }

                    generalError?.let {
                        Text(it, color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium)
                    }


                    TextButton(
                        onClick = onLoginClick,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append("Já possui uma conta? ")
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                ) {
                                    append("entre aqui")
                                }
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val nameVal     = AuthValidator.validateName(name)
                            val emailVal    = AuthValidator.validateEmail(email)
                            val passVal     = AuthValidator.validatePassword(senha)
                            val confirmVal  = AuthValidator.validatePasswordMatch(senha, confirmaSenha)

                            nameError     = if (!nameVal.isValid) nameVal.errorMessage else null
                            emailError    = if (!emailVal.isValid) emailVal.errorMessage else null
                            passwordError = if (!passVal.isValid) passVal.errorMessage else null
                            confirmError  = if (!confirmVal.isValid) confirmVal.errorMessage else null

                            if (nameVal.isValid && emailVal.isValid &&
                                passVal.isValid && confirmVal.isValid) {
                                scope.launch {
                                    try {
                                        val newId = userDao.insertUser(
                                            UserEntity(
                                                name = name.trim(),
                                                email = email.trim().lowercase(),
                                                password = senha
                                            )
                                        )
                                        sessionManager.saveSession(newId.toInt(), name.trim())
                                        onCreateAccountClick()
                                    } catch (e: Exception) {
                                        generalError = "Este e-mail já está cadastrado"
                                    }
                                }

                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = MaterialTheme.shapes.large
                    ) {
                        Text(
                            text = "Criar conta",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}
