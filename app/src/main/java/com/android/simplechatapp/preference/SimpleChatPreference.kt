package com.android.simplechatapp.preference

import android.content.Context
import android.content.SharedPreferences

class SimpleChatPreference(context: Context) {

    private val PREFERENCES_NAME = "Simple Chat"

    private val preference: SharedPreferences by lazy {
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveBoolean(key:String, value:Boolean){
        preference.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, default:Boolean = false):Boolean{
        return preference.getBoolean(key, default)
    }

    fun saveString(key:String, value:String){
        preference.edit().putString(key, value).apply()
    }

    fun getString(key: String, default:String = ""):String{
        return preference.getString(key, default).orEmpty()
    }

}