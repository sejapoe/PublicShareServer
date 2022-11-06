import csstype.px
import csstype.rgb
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useState

external interface WelcomeProps : Props {
    var id: String
}

val Welcome = FC<WelcomeProps> { props ->
    val id by useState(props.id)
    div {
        css {
            padding = 5.px
            backgroundColor = rgb(8, 97, 22)
            color = rgb(56, 246, 137)
        }
        +"Hello, $id"
    }
    div {
        css {
            padding = 16.px
            backgroundColor = rgb(255, 255, 255)
        }
        QRCode {
            value = id
            size = 256
            fgColor = rgb(0, 0, 0)
            bgColor = rgb(255, 255, 255)
            level = "L"
            title = "Scan this in mobile app"
        }
    }
}