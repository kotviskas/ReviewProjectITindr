package com.r3d1r4ph.mobile_lab2_itindr.ui.login

import android.app.Application
import android.util.Log
import com.r3d1r4ph.mobile_lab2_itindr.database.tables.auth.AuthEntity
import com.r3d1r4ph.mobile_lab2_itindr.repository.auth.AuthDBRepository
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.AuthParams
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.login.LoginService
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.register.RegisterService
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileResponse
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.profile.ProfileService
import com.r3d1r4ph.mobile_lab2_itindr.utils.resultwrapper.ResultWrapper
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AuthRepository(app: Application) {
    private val loginService = LoginService()
    private val registerService = RegisterService()
    private val authDBRepository = AuthDBRepository(app)
    private val profileService = ProfileService()

    suspend fun login(email: String, password: String) =
        loginService.login(authParams = AuthParams(email = email, password = password))

    suspend fun register(email: String, password: String) =
        registerService.register(authParams = AuthParams(email = email, password = password))

    suspend fun resetTokens(authEntity: AuthEntity) = authDBRepository.resetTokens(authEntity)

    suspend fun getProfile() = profileService.getProfile()

    suspend fun addProfile(profileResponse: ProfileResponse) {
        Log.d("profile", Json.encodeToString(profileResponse.toDomain()))
        authDBRepository.updateProfile(Json.encodeToString(profileResponse.toDomain()))
    }

}