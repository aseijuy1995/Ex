package com.example

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") { call.respondText("Hello World!", ContentType.Text.Plain) }
        }
        module()
    }
    server.start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) { jackson { enable(SerializationFeature.INDENT_OUTPUT) } }
    install(Authentication) {
        basic(name = "myauth") {
            realm = "Ktor Server"
            validate {
                if (it.name == "name" && it.password == "password") UserIdPrincipal("name") else null
            }
        }
    }

    routing {

        route("/get") {
            get {
                val param = call.request.queryParameters["param"]
                if (param == "getParam")
                    call.respond(getData)
                else
                    call.respond("")
            }
        }

        route("/head") {
            head {
                val param = call.request.queryParameters["param"]
                if (param == "headParam")
                    call.respond("")
                else
                    call.respond("")

            }
        }

        route("/post") {
            authenticate("myauth") {
//            //post params
//            post("/param") {
//                println("postParam")
//                val value = call.request.queryParameters["param"]
//                if (value == "postParam")
//                    call.respond(postParameterData)
//                else
//                    call.respond("")
//            }

                //post json
                post("/json") {
                    println("postJson")
                    val request = call.receive<PostRequest>()
                    if (request.param == "postJson")
                        call.respond(jsonData)
                    else
                        call.respond("")

                }

                //post from-data
                post("/from-data") {
                    println("postFromData")
                    val value = call.receiveParameters()["param"]
                    if (value == "postFromData")
                        call.respond(fromDataData)
                    else
                        call.respond("")
                }
            }
        }

        route("/delete") {
            authenticate("myauth") {
                //delete params
                delete("/param") {
                    println("deleteParam")
                    val value = call.request.queryParameters["param"]
                    if (value == "deleteParam")
                        call.respond(parameterData)
                    else
                        call.respond("")
                }

                //delete json
                delete("/json") {
                    println("deleteJson")
                    val request = call.receive<PostRequest>()
                    if (request.param == "deleteJson")
                        call.respond(jsonData)
                    else
                        call.respond("")

                }

                //delete from-data
                delete("/from-data") {
                    println("deleteFromData")
                    val value = call.receiveParameters()["param"]
                    if (value == "deleteFromData")
                        call.respond(fromDataData)
                    else
                        call.respond("")
                }
            }
        }

        route("/put") {
            authenticate("myauth") {
                //put params
                put("/param") {
                    println("putParam")
                    val value = call.request.queryParameters["param"]
                    if (value == "putParam")
                        call.respond(parameterData)
                    else
                        call.respond("")
                }

                //put json
                put("/json") {
                    println("putJson")
                    val request = call.receive<PostRequest>()
                    if (request.param == "putJson")
                        call.respond(jsonData)
                    else
                        call.respond("")

                }

                //put from-data
                put("/from-data") {
                    println("putFromData")
                    val value = call.receiveParameters()["param"]
                    if (value == "putFromData")
                        call.respond(fromDataData)
                    else
                        call.respond("")
                }
            }
        }

        route("/patch") {
            authenticate("myauth") {
                //patch params
                patch("/param") {
                    println("patchParam")
                    val value = call.request.queryParameters["param"]
                    if (value == "patchParam")
                        call.respond(parameterData)
                    else
                        call.respond("")
                }

                //patch json
                patch("/json") {
                    println("patchJson")
                    val request = call.receive<PostRequest>()
                    if (request.param == "patchJson")
                        call.respond(jsonData)
                    else
                        call.respond("")

                }

                //patch from-data
                patch("/from-data") {
                    println("patchFromData")
                    val value = call.receiveParameters()["param"]
                    if (value == "patchFromData")
                        call.respond(fromDataData)
                    else
                        call.respond("")
                }
            }
        }
    }
}

data class PostRequest(val param: String)