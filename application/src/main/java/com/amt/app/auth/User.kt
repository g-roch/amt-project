package com.amt.app.auth

//import kotlinx.serialization.Serializable;
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class User(val id: Int, val username: String, val role: String) {

    companion object {
        @JvmStatic
        fun fromString(data: String): User {
            return Json.decodeFromString<User>(data)
        }
    }

}