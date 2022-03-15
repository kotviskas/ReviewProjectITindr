package com.r3d1r4ph.mobile_lab2_itindr.utils

import android.content.Context
import android.content.SharedPreferences
import com.r3d1r4ph.mobile_lab2_itindr.BuildConfig
import com.r3d1r4ph.mobile_lab2_itindr.serverapi.auth.TokenResponse

object SharedPreferencesUtils {

    private const val SHARED_PREFERENCES_NAME = "${BuildConfig.APPLICATION_ID}.shared_preferences"
    private const val ACCESS_TOKEN_KEY = "accessToken"
    private const val ACCESS_TOKEN_EXPIRED_AT_KEY = "accessTokenExpiredAt"
    private const val REFRESH_TOKEN_KEY = "refreshToken"
    private const val REFRESH_TOKEN_EXPIRED_AT_KEY = "refreshTokenExpiredAt"
    private lateinit var sharedPreferences: SharedPreferences

    //todo
    fun createSharedPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
    }

    fun updateTokenInfo(tokenResponse: TokenResponse) = with(sharedPreferences.edit()) {
        putString(
            ACCESS_TOKEN_KEY,
            tokenResponse.accessToken
        )
        putString(
            ACCESS_TOKEN_EXPIRED_AT_KEY,
            tokenResponse.accessTokenExpiredAt
        )
        putString(
            REFRESH_TOKEN_KEY,
            tokenResponse.refreshToken
        )
        putString(
            REFRESH_TOKEN_EXPIRED_AT_KEY,
            tokenResponse.refreshTokenExpiredAt
        )
        apply()
    }

    fun getAccessToken(): String {
        return sharedPreferences.getString(
            ACCESS_TOKEN_KEY,
            ""
        ).toString()
    }

    fun getRefreshToken(): String {
        return sharedPreferences.getString(
            REFRESH_TOKEN_KEY,
            ""
        ).toString()
    }
}