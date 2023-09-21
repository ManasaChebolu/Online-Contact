package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
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
                call.respond(ResponseMessage("User created with ID: $userId"))
            } else
                call.respond(ResponseMessage("User mobile number should be of length 10"))
        }

        //upload contact
        post("/uploadContact") {
            val userRequest=call.receive<ContactUploadRequest>()
            val userId = userRequest.user_id
            val contacts =userRequest.contacts
            if(ContactList.containsKey(userId)) {
                if ((ContactList[userId]?.size?.plus(contacts.size)!!) > 10)
                    call.respond(ResponseMessage("One user call upload max of 10 contacts"))
                else {
                    ContactList[userId]?.addAll(contacts)
                    println(ContactList)
                    call.respond(ResponseMessage("Contacts are uploaded for user id: $userId"))
                }
            } else
                call.respond(ResponseMessage("No such user"))
        }

        //Get all contacts of a particular user
        get("/getContacts") {
            val userRequest=call.receive<Map<String,String>>()
            val userId =userRequest["user_id"] ?: throw IllegalStateException()
            if(ContactList.containsKey(userId)) {
                val contacts = ContactList[userId] ?: throw IllegalStateException()
                call.respond(ContactUploadRequest(userId,contacts))
            } else
                call.respond(ResponseMessage("No such user"))
        }
    }
}
