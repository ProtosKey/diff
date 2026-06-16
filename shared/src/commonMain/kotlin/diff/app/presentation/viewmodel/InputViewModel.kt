package diff.app.presentation.viewmodel

import diff.app.data.MainStore
import diff.app.presentation.basic.BaseViewModel

class InputViewModel(store: MainStore) : BaseViewModel(store) {
    val form = store.form
    val notification = store.notification

    fun updateEquationIndex(value: Int) {
        store.updateForm(store.form.value.copy(equationIndex = value))
    }

    fun updateStart(value: String) {
        store.updateForm(store.form.value.copy(start = value))
    }

    fun updateEnd(value: String) {
        store.updateForm(store.form.value.copy(end = value))
    }

    fun updateInitialY(value: String) {
        store.updateForm(store.form.value.copy(initialY = value))
    }

    fun updateStep(value: String) {
        store.updateForm(store.form.value.copy(step = value))
    }

    fun updateEpsilon(value: String) {
        store.updateForm(store.form.value.copy(epsilon = value))
    }
}
