package com.dmm.bootcamp.yatter2023.usecase.register

import com.dmm.bootcamp.yatter2023.domain.model.Password
import com.dmm.bootcamp.yatter2023.domain.model.Username

interface RegisterAccountUseCase {
  suspend fun execute(
    username: Username,
    password: Password
  ): RegisterAccountUseCaseResult
}
