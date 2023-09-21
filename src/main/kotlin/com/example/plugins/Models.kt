package com.example.plugins

import kotlinx.serialization.Serializable
//class that contains user details
@Serializable
data class User(val user_name:String,val mobile_no: String,val email_id:String?,val user_id:String)

//class that contains contact upload request
@Serializable
data class ContactUploadRequest(val user_id:String,val contacts:MutableList<Contacts>)

//class that contain contact details
@Serializable
data class Contacts(val first_name: String, val last_name: String,val contact_numbers: MutableList<ContactNumber>)

//class that contain contact number
@Serializable
data class ContactNumber(val number:Int,val label: String)

//class that used for json format
@Serializable
data class  ResponseMessage(val Message:String)

