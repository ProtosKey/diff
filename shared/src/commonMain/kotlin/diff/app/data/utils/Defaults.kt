package diff.app.data.utils

import diff.app.data.model.MessageType

object Defaults {
    fun exception(): String = "Неожиданная ошибка"

    fun message(): String = "Странно, я был невидимый"

    fun messageType(): MessageType = MessageType.GOOD

    fun visible(): Boolean = false
}
