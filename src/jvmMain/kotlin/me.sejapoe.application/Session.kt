package me.sejapoe.application

class Session private constructor(val id: String) {
    var isWebHandshakeComplete = false
    var isMobileHandshakeComplete = false

    companion object {
        private const val ID_LENGTH = 128
        private val CHAR_POOL = (('a'..'z') + ('A'..'Z') + ('0'..'9')).toSet()

        private val pendingIds = mutableSetOf<String>()
        private val activeSessionsIds = mutableSetOf<String>()
        private val activeSessions = mutableSetOf<Session>()

        fun startPending(): String {
            var candidateId = generateRandomId()
            while (candidateId in pendingIds || candidateId in activeSessionsIds) {
                candidateId = generateRandomId()
            }
            pendingIds.add(candidateId)
            return candidateId
        }

        fun startSession(id: String) {
            require(id.length == ID_LENGTH && id.all { it in CHAR_POOL } && id in pendingIds)
            pendingIds.remove(id)
            activeSessions.add(
                Session(id)
            )
        }

        private fun generateRandomId() = List(ID_LENGTH) { CHAR_POOL.random() }.joinToString("")
    }
}