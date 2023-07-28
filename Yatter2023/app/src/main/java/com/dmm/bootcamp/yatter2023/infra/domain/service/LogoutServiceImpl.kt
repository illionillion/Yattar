package com.dmm.bootcamp.yatter2023.infra.domain.service

import com.dmm.bootcamp.yatter2023.domain.service.LogoutService
import com.dmm.bootcamp.yatter2023.infra.pref.MePreferences

class LogoutServiceImpl(
    private val mePreferences: MePreferences
): LogoutService {
    override suspend fun execute() {
        mePreferences.clear()
    }
}