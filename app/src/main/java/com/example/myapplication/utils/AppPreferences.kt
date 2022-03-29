package com.example.myapplication.utils


import android.content.Context
import android.content.SharedPreferences

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class AppPreferences {

    companion object {
        private lateinit var sharedPreferences: SharedPreferences
        private val advancedSpec = KeyGenParameterSpec.Builder(
            "_androidx_security_master_key_",
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(256)
            .build()

        private val masterKey = MasterKey.Builder(MainApplication.applicationContext())
            .setKeyGenParameterSpec(advancedSpec)
            .build()


        fun getInstance(context: Context): SharedPreferences {
            sharedPreferences = EncryptedSharedPreferences.create(
                context,
                "xyz",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            return sharedPreferences
        }

    }
}