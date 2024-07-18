package com.fpoly.shoes_app.utility

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.fpoly.shoes_app.framework.presentation.ui.forgot.forGotEmail.ForGotEmailFragment
import javax.inject.Singleton

@Singleton
object SharedPreferencesManager {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor
    lateinit var bundle: Bundle

    private const val SPLASH_SCREEN_NOT_SHOW = "splash_screen_not_show"
    private const val VIBRATE_MODE_KEY = "vibrate_mode"
    private const val userName = "username"
    private const val userNameWait = "userNameWait"
    private const val passWord = "password"
    private const val idUser = "idUser"

    fun isSplashScreenSkipped(): Boolean = getBooleanDataByKey(SPLASH_SCREEN_NOT_SHOW)

    fun setSplashScreenSkipped(isSkipped: Boolean) {
        saveBooleanDataByKey(SPLASH_SCREEN_NOT_SHOW, isSkipped)
    }
    fun getVibrateModeState(): Boolean = getBooleanDataByKey(VIBRATE_MODE_KEY)
    fun saveVibrateModeState(isVibrateMode: Boolean) {
        saveBooleanDataByKey(VIBRATE_MODE_KEY, isVibrateMode)
    }
    fun getUserName(): String = getStringDataByKey(userName)

    fun getUserNameWait(): String = getStringDataByKey(userNameWait)

    fun setPassWord(email: String, pass: String?) {
        saveStringDataByKey(userName, email)
        saveStringDataByKey(passWord, pass)
    }

    fun setUserWait() {
        saveStringDataByKey(userNameWait, getUserName())
    }

    fun getPassWord(): String = getStringDataByKey(passWord)
    fun removeUser() {
        editor.remove(userName).apply()
        editor.remove(passWord).apply()
    }

    fun setIdUser(id: String?) {
        saveStringDataByKey(idUser, id)
    }

    fun getIdUser(): String = getStringDataByKey(idUser)
    fun removeIdUser() {
        editor.remove(idUser).apply()
    }

    fun removeUserWait() {
        editor.remove(userNameWait).apply()
    }

    private fun saveStringDataByKey(key: String?, data: String?) {
        editor.putString(key, data).apply()
    }

    private fun getStringDataByKey(key: String?): String =
        sharedPreferences.getString(key, "") ?: ""

    private fun saveBooleanDataByKey(key: String?, data: Boolean) {
        editor.putBoolean(key, data).apply()
    }

    private fun getBooleanDataByKey(key: String?): Boolean =
        sharedPreferences.getBoolean(key, false)


}