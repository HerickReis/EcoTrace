package br.com.fiap.ecotrace.data.session

import android.content.Context

class SessionManager(context: Context) {

    private val prefs = context.getSharedPreferences(
        "ecotrace_session",
        Context.MODE_PRIVATE
    )

    companion object {
        private const val KEY_USER_ID   = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val NO_USER       = -1
    }

    fun saveSession(userId: Int, userName: String) {
        prefs.edit()
            .putInt(KEY_USER_ID, userId)
            .putString(KEY_USER_NAME, userName)
            .apply()
    }
    fun getLoggedUserId(): Int = prefs.getInt(KEY_USER_ID, NO_USER)

    fun getLoggedUserName(): String = prefs.getString(KEY_USER_NAME, "") ?: ""

    fun isLoggedIn(): Boolean = getLoggedUserId() != NO_USER

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}