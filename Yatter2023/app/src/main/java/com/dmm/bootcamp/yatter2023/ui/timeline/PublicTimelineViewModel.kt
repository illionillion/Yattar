package com.dmm.bootcamp.yatter2023.ui.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmm.bootcamp.yatter2023.domain.repository.AccountRepository
import com.dmm.bootcamp.yatter2023.domain.repository.StatusRepository
import com.dmm.bootcamp.yatter2023.domain.service.CheckLoginService
import com.dmm.bootcamp.yatter2023.domain.service.LogoutService
import com.dmm.bootcamp.yatter2023.infra.domain.converter.MeConverter
import com.dmm.bootcamp.yatter2023.infra.domain.model.MeImpl
import com.dmm.bootcamp.yatter2023.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicTimelineViewModel(
    private val accountRepository: AccountRepository,
    private val statusRepository: StatusRepository,
    private val logoutService: LogoutService,
    ) : ViewModel() {
    private val _uiState: MutableStateFlow<PublicTimelineUiState> =
        MutableStateFlow(PublicTimelineUiState.empty())
    val uiState: StateFlow<PublicTimelineUiState> = _uiState
    private val _navigateToPost: SingleLiveEvent<Unit> = SingleLiveEvent()
    val navigateToPost: LiveData<Unit> = _navigateToPost
    private val _navigateToLogin: SingleLiveEvent<Unit> = SingleLiveEvent()
    val navigateToLogin: LiveData<Unit> = _navigateToLogin

    private suspend fun fetchPublicTimeline() {
        val myAccount = accountRepository.findMe()
        val statusList = statusRepository.findAllPublic() // 1
        _uiState.update {
            it.copy(
                myAccount = myAccount?.let { it -> MeConverter.convertToMe(it) } as MeImpl,
                statusList = StatusConverter.convertToBindingModel(statusList), // 2
            )
        }
    }

    fun onResume() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isLoading = true) } // 2
            fetchPublicTimeline() // 3
            _uiState.update { it.copy(isLoading = false) } // 4
        }
    }

    fun onRefresh() {
        viewModelScope.launch { // 1
            _uiState.update { it.copy(isRefreshing = true) } // 2
            fetchPublicTimeline() // 3
            _uiState.update { it.copy(isRefreshing = false) } // 4
        }
    }

    fun onClickLogout() {

        viewModelScope.launch {
            logoutService.execute()
            _navigateToLogin.value = Unit
        }

    }

    fun onClickPost() {
        _navigateToPost.value = Unit
    }

}