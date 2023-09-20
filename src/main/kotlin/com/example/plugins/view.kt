package com.example.plugins

val UsersList = mutableListOf<User>()
val ContactList:MutableMap<String,MutableList<Contacts>> = mutableMapOf()

fun generateId():String {
    val alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    return (1..6)
        .map { alphaNumeric.random() }
        .joinToString("")
}

