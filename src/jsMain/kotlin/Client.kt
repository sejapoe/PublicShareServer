import kotlinx.browser.document
import org.w3c.dom.WebSocket
import react.create
import react.dom.client.createRoot

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val root = createRoot(container)
    var id = ""
    val webSocket = WebSocket("ws://localhost:8080/pending")
    webSocket.onmessage = {
        when (val data = it.data) {
            "connected" -> {
                root.render(Connection.create {
                    this.id = id
                    this.socket = WebSocket("ws://localhost:8080/connect/web/$id")
                })
            }

            is String -> {
                id = data
                root.render(Welcome.create() {
                    this.id = data
                })
            }
        }
    }
}