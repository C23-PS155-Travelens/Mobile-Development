package com.app.travellens.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = "user")

class SharedPref (private val context : Context) {

    private val id = stringPreferencesKey("id")
    private val username = stringPreferencesKey("username")
    private val email = stringPreferencesKey("email")
    private val photo = stringPreferencesKey("photo")
    private val address = stringPreferencesKey("address")
    private val phone = stringPreferencesKey("phone")

    suspend fun saveLog(ids : String, usernames : String, emails : String, photos : String, addresss : String, phones : String){
        context.dataStore.edit {
            it[id] = ids
            it[username] = usernames
            it[email] = emails
            it[photo] = photos
            it[address] = addresss
            it[phone] = phones

        }
    }

    suspend fun saveAfterUpdate( usernames : String, emails : String, photos : String, addresss : String, phones : String){
        context.dataStore.edit {
            it[username] = usernames
            it[email] = emails
            it[photo] = photos
            it[address] = addresss
            it[phone] = phones

        }
    }

    val getId : Flow<String> = context.dataStore.data
        .map {
            it[id]?: "Undefined"
        }

    val getPhone : Flow<String> = context.dataStore.data
        .map {
            it[phone]?: "Undefined"
        }

    val getAddress : Flow<String> = context.dataStore.data
        .map {
            it[address]?: "Undefined"
        }

    val getAll : Flow<String> = context.dataStore.data
        .map {
            it[id] ?: "Undefined"
            it[username] ?: "Undefined"
            it[email] ?: "Undefined"
            it[photo] ?: "Undefined"
            it[address] ?: "Undefined"
        }

    val getEmail : Flow<String> = context.dataStore.data
        .map {
            it[email] ?: "Undefined"
        }

    val getNama : Flow<String> = context.dataStore.data
        .map {
            it[username] ?: "Undefined"
        }

    suspend fun removeSession(){
        context.dataStore.edit {
            it.clear()

        }
    }
}