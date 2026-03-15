package br.com.fiap.ecotrace.model

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String = ""
)

object AuthValidator {

    fun validateEmail(email: String): ValidationResult {
        if (email.isBlank())
            return ValidationResult(false, "E-mail não pode ser vazio")
        if (!email.contains("@") || !email.contains("."))
            return ValidationResult(false, "E-mail inválido")
        return ValidationResult(true)
    }

    fun validatePassword(password: String): ValidationResult {
        if (password.isBlank())
            return ValidationResult(false, "Senha não pode ser vazia")
        if (password.length < 6)
            return ValidationResult(false, "Senha deve ter no mínimo 6 caracteres")
        return ValidationResult(true)
    }

    fun validatePasswordMatch(password: String, confirm: String): ValidationResult {
        if (password != confirm)
            return ValidationResult(false, "As senhas não coincidem")
        return ValidationResult(true)
    }

    fun validateName(name: String): ValidationResult {
        if (name.isBlank())
            return ValidationResult(false, "Nome não pode ser vazio")
        if (name.length < 2)
            return ValidationResult(false, "Nome muito curto")
        return ValidationResult(true)
    }
}