package diff.app.di

import diff.app.data.MainStore
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    single { MainStore() }
}

private val koinInit by lazy { startKoin { modules(appModule) } }

fun initKoin() {
    koinInit
}
