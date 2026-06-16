package diff.app.data

import diff.app.data.model.InputForm
import diff.app.data.model.MessageType
import diff.app.data.model.Notification
import diff.app.data.model.Storage
import diff.app.domain.model.Epsilon
import diff.app.domain.model.Equation
import diff.app.domain.model.Interval
import diff.app.domain.model.Point
import diff.app.domain.model.Step
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainStore {
    private val _equation = MutableStateFlow<Equation?>(null)
    val equation = _equation.asStateFlow()

    private val _interval = MutableStateFlow<Interval?>(null)
    val interval = _interval.asStateFlow()

    private val _initial = MutableStateFlow<Point?>(null)
    val initial = _initial.asStateFlow()

    private val _step = MutableStateFlow<Step?>(null)
    val step = _step.asStateFlow()

    private val _epsilon = MutableStateFlow<Epsilon?>(null)
    val epsilon = _epsilon.asStateFlow()

    private val _storage = MutableStateFlow<Storage?>(null)
    val storage = _storage.asStateFlow()

    private val _Input_form = MutableStateFlow(InputForm())
    val form = _Input_form.asStateFlow()

    private val _notification = MutableStateFlow(Notification())
    val notification = _notification.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun startLoading() {
        _isLoading.update { true }
    }

    fun endLoading() {
        _isLoading.update { false }
    }

    fun updateEquation(value: Equation?) {
        _equation.update { value }
    }

    fun updateInterval(value: Interval?) {
        _interval.update { value }
    }

    fun updateInitial(value: Point?) {
        _initial.update { value }
    }

    fun updateStep(value: Step?) {
        _step.update { value }
    }

    fun updateEpsilon(value: Epsilon?) {
        _epsilon.update { value }
    }

    fun updateStorage(value: Storage?) {
        _storage.update { value }
    }

    fun clearStorage() {
        _storage.update { null }
    }

    fun updateForm(value: InputForm) {
        _Input_form.update { value }
    }

    fun showMessage(message: String, messageType: MessageType) {
        _notification.update {
            it.copy(message = message, messageType = messageType, isVisible = true)
        }
    }

    fun hideMessage() {
        _notification.update {
            it.copy(isVisible = false)
        }
    }
}
