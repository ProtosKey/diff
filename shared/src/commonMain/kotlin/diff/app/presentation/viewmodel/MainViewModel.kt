package diff.app.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import diff.app.data.MainStore
import diff.app.domain.model.Point
import diff.app.domain.model.Problem
import diff.app.domain.utils.MethodKind
import diff.app.presentation.basic.BaseViewModel
import diff.app.presentation.mapper.DataMapper
import diff.app.presentation.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class MainViewModel(store: MainStore) : BaseViewModel(store) {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    val notification = store.notification

    init {
        store.storage.onEach { storage ->
            if (storage == null) {
                _state.update { MainState() }
                return@onEach
            }
            val solutions = storage.solutions.associate { solution ->
                solution.method to solution.points.map(DataMapper::mapTo)
            }
            val visible = solutions.mapValues { (kind, _) ->
                _state.value.visible[kind] ?: true
            }
            val exactDense = sampleDenseExact(storage.problem).map(DataMapper::mapTo)
            _state.update {
                it.copy(
                    solutions = solutions,
                    exact = exactDense,
                    visible = visible,
                )
            }
        }.launchIn(viewModelScope)
    }

    fun toggleVisibility(kind: MethodKind) {
        _state.update {
            val next = it.visible.toMutableMap()
            next[kind] = !(next[kind] ?: true)
            it.copy(visible = next)
        }
    }

    private fun sampleDenseExact(problem: Problem): List<Point> {
        val initial = problem.point
        val real = problem.equation.real
        val fineStep = problem.interval.length / DENSE_SAMPLES
        return (0..DENSE_SAMPLES).map { i ->
            val x = initial.x + i * fineStep
            Point(x, real.apply(x, initial))
        }
    }

    private companion object {
        const val DENSE_SAMPLES = 300
    }
}
