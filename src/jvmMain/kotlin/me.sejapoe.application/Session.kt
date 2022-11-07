package me.sejapoe.application

import io.ktor.websocket.*

class Session private constructor(val id: String) {
    val isWebHandshakeComplete
        get() = webSocketSession != null
    val isMobileHandshakeComplete
        get() = mobileSocketSession != null
    var webSocketSession: WebSocketSession? = null
        set(value) {
            println("Set websocket to $value")
            field = value
        }
    var mobileSocketSession: WebSocketSession? = null
        set(value) {
            println("Set websocket to $value")
            field = value
        }

    companion object {
        private const val ID_LENGTH = 128
        private val CHAR_POOL = (('a'..'z') + ('A'..'Z') + ('0'..'9')).toSet()

        private val pendingIds = mutableSetOf<String>()
        private val activeSessions = mutableMapOf<String, Session>()

        fun startPending(): String {
            var candidateId = generateRandomId()
            while (candidateId in pendingIds || candidateId in activeSessions.keys) {
                candidateId = generateRandomId()
            }
            pendingIds.add(candidateId)
            return candidateId
        }

        private fun startSession(id: String): Session {
            require(id.length == ID_LENGTH && id.all { it in CHAR_POOL } && id in pendingIds)
            pendingIds.remove(id)
            val session = Session(id)
            activeSessions[id] = session
            return session
        }

        fun getOrStartSession(id: String): Session = activeSessions.getOrDefault(id, startSession(id))

        private fun generateRandomId() = List(ID_LENGTH) { CHAR_POOL.random() }.joinToString("")
    }
}