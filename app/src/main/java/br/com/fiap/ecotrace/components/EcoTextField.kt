package br.com.fiap.ecotrace.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ecotrace.ui.theme.EcoTraceTheme

@Composable
fun EcoTextField(
    value: String,                    // texto atual do campo
    onValueChange: (String) -> Unit,  // O que fazer quando o usuário digita
    label: String,                    // "email" ou "Senha"
    leadingIcon: ImageVector,         // Ícone da esquerda
    isPassword: Boolean = false,      // Se true, oculta o texto
    modifier: Modifier = Modifier
) {
    // isPasswordVisible controla o olho de mostrar/ocultar senha
    // Começa como false (senha oculta por padrão)
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },

        // Ícone da esquerda
        leadingIcon = {
            Icon(imageVector = leadingIcon, contentDescription = label)
        },

        // Ícone da direita — só aparece se for campo de senha
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    // Troca o ícone baseado no estado
                    val icon = if (isPasswordVisible)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff

                    Icon(imageVector = icon, contentDescription = "Mostrar senha")
                }
            }
        },

        // VisualTransformation esconde/mostra a senha
        // PasswordVisualTransformation() → oculta a senha com bolinhas
        // VisualTransformation.None → mostra o texto normal
        visualTransformation = if (isPassword && !isPasswordVisible)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,

        modifier = modifier.fillMaxWidth(),

        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondary,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
        ),
        shape = MaterialTheme.shapes.medium
    )
}

@Preview(showBackground = true)
@Composable
private fun EcoTextFieldPreview() {
    EcoTraceTheme() {
        EcoTextField(
            value = "",
            onValueChange = {},
            label = "Password",
            leadingIcon = Icons.Default.Message,
            isPassword = true
        )

    }
    
}