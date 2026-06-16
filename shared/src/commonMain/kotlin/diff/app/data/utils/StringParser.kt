package diff.app.data.utils

import diff.app.domain.exception.InitException

object StringParser {
    private val TRAILING_ZEROS = "0*$".toRegex()
    private val TRAILING_DOT = "\\.$".toRegex()
    private val LEADING_ZEROS = "^0+(?=\\d)".toRegex()

    fun prepareNumber(value: String): String {
        var result = value.replace(",", ".").trim()
        if (result.isEmpty()) return ""

        result = when {
            result.startsWith(".") -> "0$result"
            result.startsWith("-.") -> result.replace("-.", "-0.")
            else -> result
        }

        return removeZeros(
            if (result.startsWith("-")) {
                "-" + result.substring(1).replaceFirst(LEADING_ZEROS, "")
            } else {
                result.replaceFirst(LEADING_ZEROS, "")
            }
        )
    }

    fun parseDouble(value: String, name: String): Double {
        val prepared = prepareNumber(value)
        if (prepared.isEmpty()) {
            throw InitException("$name: значение не может быть пустым")
        }
        return prepared.toDoubleOrNull()
            ?: throw InitException("$name: «$value» должно быть числом")
    }

    private fun removeZeros(value: String): String {
        if (value.isEmpty() || !value.contains(".")) return value
        return value
            .replace(TRAILING_ZEROS, "")
            .replace(TRAILING_DOT, "")
    }
}
