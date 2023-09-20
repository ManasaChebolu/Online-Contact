package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Application.configureRouting() {
    routing {
        //Creation of user Details
        post("/createUser") {
            val userRequest = call.receive<Map<String, String>>()
            val userName = userRequest["user_name"] ?: throw IllegalStateException()
            val mobileNo = userRequest["mobile_no"] ?: throw IllegalStateException()
            val email = userRequest["email_id"]
            if (mobileNo.length == 10) {
                val userId = generateId()
                UsersList.add(User(userName, mobileNo, email, userId))
                ContactList[userId]= mutableListOf()
                call.respondText("User created with ID: $userId")
            } else
                call.respond("User mobile number should be of length 10")
        }

        //upload contact
        post("/uploadContact") {
            val userRequest=call.receive<ContactUploadRequest>()
            val userId = userRequest.user_id
            val contacts =userRequest.contacts
            if(ContactList.containsKey(userId)) {
                if ((ContactList[userId]?.size?.plus(contacts.size)!!) > 10)
                    call.respond("One user call upload max of 10 contacts")
                else {
                    ContactList[userId]?.addAll(contacts)
                    println(ContactList)
                    call.respond("Contacts are uploaded for user id: $userId")
                }
            } else
                call.respond("No such user")
        }

        //Get all contacts of a particular user
        get("/getContacts") {
            val userRequest=call.receive<Map<String,String>>()
            val userId =userRequest["user_id"]
            if(ContactList.containsKey(userId)) {
                val contacts = ContactList[userId]
                val json= Json { encodeDefaults=true }
                val jsonContacts=json.encodeToString(contacts)
                call.respond("All the contacts of userId $userId are : \n  $jsonContacts")
            } else
                call.respond("No such user")
        }
    }
}
