package diff.app.domain.utils

enum class MethodKind(
    val label: String,
    val short: String,
    val order: Int
) {
    EULER(label = "Усовершенствованный метод Эйлера", short = "Эйлер", order = 2),
    RUNGE(label = "Метод Рунге-Кутта 4-го порядка", short = "Рунге-Кутта", order = 4),
    MILNE(label = "Метод Милна", short = "Милна", order = 4),
}