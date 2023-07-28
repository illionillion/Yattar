package com.dmm.bootcamp.yatter2023.ui.profile

import com.dmm.bootcamp.yatter2023.ui.timeline.bindingmodel.StatusBindingModel

data class ProfileUiState(
    val bindingModel: ProfileBindingModel,
    val statusList: List<StatusBindingModel>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
) {
    companion object {
        fun empty() = ProfileUiState(
            bindingModel = ProfileBindingModel(
                username = "",
                displayName = "",
                note = "",
                avatar = null,
                header = null,
                followingCount = 0,
                followerCount = 0
            ),
            statusList = listOf(),
            isLoading = false,
            isRefreshing = false,
        )
    }
}