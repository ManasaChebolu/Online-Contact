package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.lang.IllegalStateException


fun Application.exception() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            if(cause is IllegalStateException )
                call.respondText(text = "Provide all required fields", status = HttpStatusCode.InternalServerError)
            else
                call.respondText(text = "provide both UserId and contacts", status = HttpStatusCode.NotFound)
        }
    }
}