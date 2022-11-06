import kotlinx.browser.document
import kotlinx.browser.window
import react.create
import react.dom.client.createRoot
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.js.Promise

suspend fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val id = window.fetch("http://localhost:8080/startPending").then {
        it.text()
    }.then {
        it
    }.await()

    val welcome = Welcome.create {
        this.id = id
    }
    createRoot(container).render(welcome)
}

suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
    then({ cont.resume(it) }, { cont.resumeWithException(it) })
}