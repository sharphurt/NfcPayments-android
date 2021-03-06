package net.catstack.nfcpay.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import net.catstack.nfcpay.domain.ProfileModel
import net.catstack.nfcpay.domain.TokenModel
import net.catstack.nfcpay.domain.network.request.DeviceInfo

class AccountRepository(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) {
    var userToken: TokenModel?
        get() {
            val token = sharedPreferences.getString(::userToken.name, null) ?: return null
            return gson.fromJson(token, TokenModel::class.java)
        }
        set(value) {
            sharedPreferences.edit()
                .putString(::userToken.name, gson.toJson(value))
                .apply()
        }

    lateinit var profileModel: ProfileModel

    lateinit var deviceInfo: DeviceInfo

    var email: String?
        get() = sharedPreferences.getString(::email.name, null)
        set(value) {
            sharedPreferences.edit()
                .putString(::email.name, value)
                .apply()
        }

    var password: String?
        get() = sharedPreferences.getString(::password.name, null)
        set(value) {
            sharedPreferences.edit()
                .putString(::password.name, value)
                .apply()
        }

    fun isUserAuthorized() = userToken != null

    fun clearToken() {
        userToken = null
    }

    fun logout() {
        userToken = null
        email = null
        password = null
    }
}