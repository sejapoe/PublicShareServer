import csstype.px
import csstype.rgb
import emotion.react.css
import org.w3c.dom.WebSocket
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState

external interface ConnectionProps : Props {
    var id: String
    var socket: WebSocket
}

val Connection = FC<ConnectionProps> { props ->
    val id by useState(props.id)
    val socket by useState(props.socket)
    div {
        css {
            padding = 5.px
            backgroundColor = rgb(158, 22, 22)
            color = rgb(255, 200, 200)
        }
        +"Hello, $id"
    }
    socket.onmessage = {
        println("Connection: ${it.data}")
    }
}