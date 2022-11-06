@file:JsModule("react-qr-code")
@file:JsNonModule

import csstype.Color
import react.ComponentClass
import react.Props

@JsName("default")
external val QRCode: ComponentClass<QRCodeProps>

external interface QRCodeProps : Props {
    var bgColor: Color?
    var fgColor: Color?
    var level: String?
    var size: Int?
    var title: String?
    var value: String?
}