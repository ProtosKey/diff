package diff.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import diff.app.data.MainStore
import diff.app.presentation.basic.BaseViewModel
import diff.app.presentation.state.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ResultViewModel(store: MainStore) : BaseViewModel(store) {
    private val _state = MutableStateFlow(ResultState())
    val state = _state.asStateFlow()
    val notification = store.notification

    init {
        combine(store.storage, store.isLoading) { storage, isLoading ->
            storage to isLoading
        }.onEach { (storage, isLoading) ->
            if (storage == null) {
                _state.update { ResultState(isLoading = isLoading) }
                return@onEach
            }
            _state.update {
                it.copy(
                    results = storage.results.associateBy { result -> result.kind },
                    exact = storage.exact,
                    isLoading = isLoading,
                )
            }
        }.launchIn(viewModelScope)
    }
}
