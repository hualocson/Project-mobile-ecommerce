package com.app.e_commerce_app.data.services

import com.app.e_commerce_app.base.network.BaseRemoteService
import com.app.e_commerce_app.common.AppSharePreference
import com.app.e_commerce_app.model.token.TokenModel

class TokenRemoteService(private val appSharePreference: AppSharePreference) : BaseRemoteService() {
    companion object {
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val REFRESH_TOKEN_KEY = "refresh_token"
        private const val REMEMBER = "remember_me"
    }

    fun saveToken(accessToken: String, refreshToken: String) {
        saveAccessToken(accessToken)
        saveRefreshToken(refreshToken)
    }

    fun saveToken(tokenModel: TokenModel) {
        saveToken(tokenModel.accessToken, tokenModel.refreshToken)
    }

    private fun saveAccessToken(token: String) {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        sharedPreferences?.edit()?.putString(ACCESS_TOKEN_KEY, token)?.apply()
    }

    private fun saveRefreshToken(token: String) {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        sharedPreferences?.edit()?.putString(REFRESH_TOKEN_KEY, token)?.apply()
    }

    fun getAccessToken(): String? {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        return sharedPreferences?.getString(ACCESS_TOKEN_KEY, null)
    }

    fun setRemember(remember: Boolean) {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        sharedPreferences?.edit()?.putBoolean(REMEMBER, remember)?.apply()
    }

    fun getRemember(): Boolean? {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        return sharedPreferences?.getBoolean(REMEMBER, false)
    }

    fun getRefreshToken(): String? {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        return sharedPreferences?.getString(REFRESH_TOKEN_KEY, null)
    }

    fun removeToken() {
        val sharedPreferences = appSharePreference.getSharedPreferences()
        sharedPreferences?.edit()?.remove(ACCESS_TOKEN_KEY)?.apply()
        sharedPreferences?.edit()?.remove(REFRESH_TOKEN_KEY)?.apply()
        sharedPreferences?.edit()?.putBoolean(REMEMBER, false)?.apply()
    }
}