package diff.app.data.model

data class InputForm(
    val equationIndex: Int = 0,
    val start: String = "0",
    val end: String = "1",
    val initialY: String = "1",
    val step: String = "0.1",
    val epsilon: String = "0.001",
)
