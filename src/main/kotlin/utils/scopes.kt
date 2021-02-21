package utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren

fun uiScope() = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

fun serviceScope() = CoroutineScope(Dispatchers.IO + SupervisorJob())

fun CoroutineScope.clear() = coroutineContext.cancelChildren()