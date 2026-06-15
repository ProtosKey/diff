package diff.app.domain.model

enum class MethodKind(val label: String, val order: Int) {
    EULER(label = "Усовершенствованный метод Эйлера", order = 2),
    RUNGE(label = "Метод Рунге-Кутта 4-го порядка", order = 4),
    MILNE(label = "Метод Милна", order = 4),
}
