package io.github.starlight220.robocalc

import react.StateInstance
import react.StateSetter

val <T> StateInstance<T>.value: T
    get() = this.component1()
val <T> StateInstance<T>.set: StateSetter<T>
    get() = this.component2()

internal fun List<StateInstance<Double>>.fold(untilIndex: Int): Double =
    this.subList(0, untilIndex + 1).fold(1.0) { acc, (value, _) -> acc * value }
