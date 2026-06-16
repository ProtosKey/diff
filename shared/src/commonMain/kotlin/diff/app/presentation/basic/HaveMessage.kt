package diff.app.presentation.basic

import diff.app.data.model.MessageType

interface HaveMessage {
    fun hideMessage()
    fun showMessage(message: String, messageType: MessageType)
}
