package com.myProj.sample.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

/**
 * Singleton class for shared preference helper
 */
class SharedPreferenceHelper {

    companion object {

        private const val PREF_TIME = "Pref Time"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferenceHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferenceHelper = instance ?: synchronized(LOCK) {
            instance ?: buildHelper(context).also {
                instance = it
            }
        }

        private fun buildHelper(context: Context) : SharedPreferenceHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferenceHelper()
        }
    }

    fun saveUpdateTime(time: Long) {
        prefs?.edit(commit = true) {
            putLong(PREF_TIME, time)
        }
    }

    fun getUpdateTime() = prefs?.getLong(PREF_TIME, 0L)

    fun getCacheDuration() = prefs?.getString("pref_cache_duration" , "")
}