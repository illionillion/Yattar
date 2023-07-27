package com.dmm.bootcamp.yatter2023.ui.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RegisterPage(viewModel: RegisterViewModel) {
    val uiState: RegisterUiState by viewModel.uiState.collectAsStateWithLifecycle()

    RegisterTemplate(
        userName = uiState.registerBindingModel.username,
        onChangedUserName = viewModel::onChangedUsername,
        password = uiState.registerBindingModel.password,
        onChangedPassword = viewModel::onChangedPassword,
        isEnableRegister = uiState.isEnableRegister,
        isLoading = uiState.isLoading,
        onClickLogin = viewModel::onClickLogin,
        onClickRegister = viewModel::onClickRegister,
    )
}