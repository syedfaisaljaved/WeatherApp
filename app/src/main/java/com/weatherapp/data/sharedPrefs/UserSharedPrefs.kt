package com.weatherapp.data.sharedPrefs

import android.content.Context

class UserSharedPrefs(context: Context) {

    val key_user_id = "user_name"
    val key_unit = "unit"

    var preff = context.getSharedPreferences(KEY_SHARED_PREF_NAME, Context.MODE_PRIVATE)
    var prefEditor = preff.edit()

    companion object {
        private var INSTANCE: UserSharedPrefs? = null
        val KEY_SHARED_PREF_NAME = "fluentLifeSharefPref"

        fun getSharedPref(context: Context): UserSharedPrefs {
            if (INSTANCE == null) {
                INSTANCE = UserSharedPrefs(context)
            }
            return INSTANCE as UserSharedPrefs
        }
    }

    fun setUsername(username: String) {
        prefEditor.putString(key_user_id, username)
        prefEditor.commit()
    }

    fun getUsername(): String {
        return preff.getString(key_user_id, "")!!
    }

    fun getUnit():String {
        return preff.getString(key_unit, "metric")!!
    }

    fun setUnit(unit : String){
        prefEditor.putString(key_unit,unit)
        prefEditor.commit()
    }

}