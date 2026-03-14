package br.com.fiap.ecotrace.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.fiap.ecotrace.model.DialogStep
import br.com.fiap.ecotrace.model.EmissionCategory
import br.com.fiap.ecotrace.model.EmissionFactors

// Modelo que representa um registro completo antes de salvar
data class EmissionRecord(
    val category: EmissionCategory,
    val type: String,       // "Carro", "Ônibus" / "Carne", "Vegetais"
    val quantity: Float,    // km ou gramas
    val cokg: Float
)

@Composable
fun AddEmissionDialog(
    onDismiss: () -> Unit,
    onConfirm: (EmissionRecord) -> Unit  // devolve o registro para a tela pai
) {
    // Estado interno do dialog
    var currentStep by remember { mutableStateOf(DialogStep.CATEGORY) }
    var selectedCategory by remember { mutableStateOf<EmissionCategory?>(null) }
    var selectedType by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var dropdownExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                // O título muda conforme o passo atual
                text = when (currentStep) {
                    DialogStep.CATEGORY -> "O que deseja registrar?"
                    DialogStep.DETAILS  -> "Preencha os detalhes"
                },
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            // AnimatedContent troca o conteúdo com animação
            // quando o targetState (currentStep) muda
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    // Desliza para esquerda ao avançar
                    slideInHorizontally { it } togetherWith slideOutHorizontally { -it }
                },
                label = "dialog_step_transition"
            ) { step ->
                when (step) {
                    DialogStep.CATEGORY -> CategoryStep(
                        onCategorySelected = { category ->
                            selectedCategory = category
                            currentStep = DialogStep.DETAILS
                        }
                    )
                    DialogStep.DETAILS -> DetailsStep(
                        category = selectedCategory!!,
                        selectedType = selectedType,
                        quantity = quantity,
                        dropdownExpanded = dropdownExpanded,
                        onTypeSelected = {
                            selectedType = it
                            dropdownExpanded = false
                        },
                        onDropdownToggle = { dropdownExpanded = !dropdownExpanded },
                        onQuantityChange = { quantity = it }
                    )
                }
            }
        },
        confirmButton = {
            // Botão de confirmação só aparece no passo 2
            if (currentStep == DialogStep.DETAILS) {
                Button(
                    onClick = {
                        val qty = quantity.toFloatOrNull() ?: return@Button
                        onConfirm(
                            EmissionRecord(
                                category = selectedCategory!!,
                                type = selectedType,
                                quantity = qty,
                                cokg = EmissionFactors.calculate(selectedCategory!!, selectedType, qty)

                            )
                        )
                        onDismiss()
                    },
                    // Só habilita se os campos estiverem preenchidos
                    enabled = selectedType.isNotEmpty() && quantity.isNotEmpty()
                ) {
                    Text("Salvar")
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    // Voltar = volta para o passo anterior, não fecha
                    if (currentStep == DialogStep.DETAILS) {
                        currentStep = DialogStep.CATEGORY
                        selectedType = ""
                        quantity = ""
                    } else {
                        onDismiss()
                    }
                }
            ) {
                Text(if (currentStep == DialogStep.DETAILS) "Voltar" else "Cancelar")
            }
        }
    )
}

// ── Passo 1 — Escolha de categoria ───────────────────────

@Composable
private fun CategoryStep(
    onCategorySelected: (EmissionCategory) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        // Cada botão de categoria avança o dialog automaticamente
        // sem precisar de um botão "próximo" separado — UX mais fluida
        CategoryButton(
            label = "Transporte",
            icon = { Icon(Icons.Default.DirectionsCar, contentDescription = null) },
            onClick = { onCategorySelected(EmissionCategory.TRANSPORT) }
        )
        CategoryButton(
            label = "Alimentação",
            icon = { Icon(Icons.Default.Restaurant, contentDescription = null) },
            onClick = { onCategorySelected(EmissionCategory.FOOD) }
        )
    }
}

@Composable
private fun CategoryButton(
    label: String,
    icon: @Composable () -> Unit,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        icon()
        Spacer(modifier = Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.titleMedium)
    }
}

// ── Passo 2 — Detalhes do registro ───────────────────────

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun DetailsStep(
    category: EmissionCategory,
    selectedType: String,
    quantity: String,
    dropdownExpanded: Boolean,
    onTypeSelected: (String) -> Unit,
    onDropdownToggle: () -> Unit,
    onQuantityChange: (String) -> Unit
) {
    // Opções mudam conforme a categoria selecionada no passo 1
    val typeOptions = when (category) {
        EmissionCategory.TRANSPORT -> listOf("Carro", "Ônibus", "Bicicleta", "A pé")
        EmissionCategory.FOOD      -> listOf("Carne", "Frango", "Vegetais", "Laticínios")
    }

    val quantityLabel = when (category) {
        EmissionCategory.TRANSPORT -> "Distância percorrida (km)"
        EmissionCategory.FOOD      -> "Quantidade consumida (g)"
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { onDropdownToggle() }
        ) {
            OutlinedTextField(
                value = selectedType,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { onDropdownToggle() }
            ) {
                typeOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = { onTypeSelected(option) }
                    )
                }
            }
        }

        OutlinedTextField(
            value = quantity,
            onValueChange = onQuantityChange,
            label = { Text(quantityLabel) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}