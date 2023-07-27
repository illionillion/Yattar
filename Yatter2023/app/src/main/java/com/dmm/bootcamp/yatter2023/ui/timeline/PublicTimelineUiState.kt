package com.dmm.bootcamp.yatter2023.ui.timeline

import com.dmm.bootcamp.yatter2023.domain.model.AccountId
import com.dmm.bootcamp.yatter2023.domain.model.Me
import com.dmm.bootcamp.yatter2023.domain.model.Username
import com.dmm.bootcamp.yatter2023.infra.domain.model.MeImpl
import com.dmm.bootcamp.yatter2023.ui.timeline.bindingmodel.StatusBindingModel
import java.net.URL

data class PublicTimelineUiState(
    val myAccount: MeImpl,
    val statusList: List<StatusBindingModel>,
    val isLoading: Boolean,
    val isRefreshing: Boolean,
) {
    companion object {
        fun empty(): PublicTimelineUiState = PublicTimelineUiState(
            myAccount = MeImpl(
                id = AccountId(""),
                username = Username(""),
                displayName = null,
                note = null,
                avatar = null,
                header = null,
                followerCount = 0,
                followingCount = 0,
            ),
            statusList = emptyList(),
            isLoading = false,
            isRefreshing = false,
        )
    }
}