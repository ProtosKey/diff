package diff.app.data.model

import diff.app.data.utils.Defaults

data class Notification(
    val message: String = Defaults.message(),
    val messageType: MessageType = Defaults.messageType(),
    val isVisible: Boolean = Defaults.visible(),
)
