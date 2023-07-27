package com.dmm.bootcamp.yatter2023.ui.register

data class RegisterUiState(
    val registerBindingModel: RegisterBindingModel,
    val isLoading: Boolean,
    val validUsername: Boolean,
    val validPassword: Boolean,
) {
    val isEnableRegister: Boolean = validUsername && validPassword

    companion object {
        fun empty(): RegisterUiState = RegisterUiState(
            registerBindingModel = RegisterBindingModel(
                username = "",
                password = ""
            ),
            validUsername = false,
            validPassword = false,
            isLoading = false,
        )
    }
}