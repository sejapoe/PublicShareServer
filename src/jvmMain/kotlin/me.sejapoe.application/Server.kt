package me.sejapoe.application

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.html.*
import java.time.Duration

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
        div {
            id = "root"
        }
        script(src = "/static/PublicShareServer.js") {}
    }
}

fun main() {
    embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
        install(WebSockets) {
            pingPeriod = Duration.ofSeconds(15)
            timeout = Duration.ofSeconds(15)
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        routing {
            get("/") {
                call.respondHtml(HttpStatusCode.OK, HTML::index)
            }
            get("/startPending") {
                call.respond(HttpStatusCode.OK, Session.startPending())
            }
            webSocket("/pending") {
                send(Session.startPending())
                launch {
                    delay(3000)
                    println("Sent")
                    send(Frame.Text("connected"))
                    close()
                }
                for (frame in incoming) {
                    println(frame.data)
                }
            }
            webSocket("/connect/{type}/{id}") {
                call.parameters["id"]?.let {
                    val session = Session.getOrStartSession(it)
                    when (call.parameters["type"]) {
                        "mobile" -> session.mobileSocketSession = this@webSocket
                        "web" -> {
                            session.webSocketSession = this@webSocket
                            session.webSocketSession?.send(Frame.Text("ping"))
                        }
                    }
                    for (frame in incoming) {
                        println(frame.data)
                    }
                }
            }
            static("/static") {
                resources()
            }
        }
    }.start(wait = true)
}