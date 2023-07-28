package com.dmm.bootcamp.yatter2023.ui.profile

//package com.dmm.bootcamp.yatter2023.ui.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun ProfilePage(
    viewModel: ProfileViewModel
) {
    val uiState: ProfileUiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileTemplate(
        username = uiState.bindingModel.username,
        displayName = uiState.bindingModel.displayName,
        note = uiState.bindingModel.note,
        avatar = uiState.bindingModel.avatar,
        header = uiState.bindingModel.header,
        followingCount = uiState.bindingModel.followingCount,
        followerCount = uiState.bindingModel.followerCount,
        statusList = uiState.statusList,
        isLoading = uiState.isLoading,
        isRefreshing = uiState.isRefreshing,
        onRefresh = viewModel::onRefresh,
        onClickEdit = viewModel::onClickEdit
    )
}