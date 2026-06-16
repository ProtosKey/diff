package diff.app.presentation.basic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import diff.app.data.MainStore
import diff.app.data.model.MessageType
import diff.app.data.utils.Defaults
import diff.app.data.utils.SolveAction
import diff.app.domain.exception.InitException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class BaseViewModel(protected val store: MainStore) : ViewModel(), HaveMessage {
    private var messageJob: Job? = null

    override fun hideMessage() {
        messageJob?.cancel()
        store.hideMessage()
    }

    override fun showMessage(message: String, messageType: MessageType) {
        messageJob?.cancel()
        messageJob = viewModelScope.launch {
            if (store.notification.value.isVisible) {
                store.hideMessage()
                delay(250)
            }
            store.showMessage(message, messageType)
        }
    }

    fun solve() {
        if (store.isLoading.value) return
        viewModelScope.launch(Dispatchers.Default) {
            store.startLoading()
            try {
                val storage = SolveAction.solve(store.form.value)
                val problem = storage.problem
                store.updateEquation(problem.equation)
                store.updateInterval(problem.interval)
                store.updateInitial(problem.point)
                store.updateStep(problem.step)
                store.updateEpsilon(problem.epsilon)
                store.updateStorage(storage)
                showMessage("Готово", MessageType.GOOD)
            } catch (e: InitException) {
                showMessage(e.message ?: Defaults.exception(), MessageType.ERROR)
            } catch (e: Exception) {
                showMessage(e.message ?: Defaults.exception(), MessageType.ERROR)
            } finally {
                store.endLoading()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        messageJob?.cancel()
    }
}
