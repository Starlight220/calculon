import csstype.*
import emotion.react.css
import kotlinx.browser.document
import react.*
import react.dom.client.createRoot
import react.dom.html.ReactHTML.div

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    createRoot(container).render(App.create {})
}

val <T> StateInstance<T>.value: T
    get() = this.component1()
val <T> StateInstance<T>.set: StateSetter<T>
    get() = this.component2()

private fun List<StateInstance<Double>>.fold(untilIndex: Int): Double =
    this.subList(0, untilIndex + 1).fold(1.0) { acc, (value, _) -> acc * value }

val App = FC<Props> {
    val stateTop = useState(0.0)
    val convertersTop = doubleArrayOf(1.0, 0.0, 0.0, 0.0).map { useState(it) }
    val stateBottom = useState(0.0)
    val convertersBottom = doubleArrayOf(1.0, 0.0, 0.0, 0.0).map { useState(it) }

    div {
        css {
            display = Display.flex
            flexDirection = FlexDirection.row
            backgroundColor = rgb(200, 10, 10)
        }
        fun entryPair(title: String, index: Int, label1: String, label2: String) {
            div {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    borderColor = rgb(200, 0, 0)
                }
                +title
                entry(
                    state = stateTop, label = label1, k = convertersTop.fold(index)
                )
                entry(
                    state = stateBottom, label = label2, k = convertersBottom.fold(index)
                )
            }
        }

        fun singleConverter(title: String, index: Int, label: String) {
            div {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    borderColor = rgb(200, 0, 0)
                }
                +title
                val (value, setter) = convertersTop[index]
                Converter {
                    this.label = label
                    this.value = value
                    this.set = {
                        setter(it)
                        convertersBottom[index].set(1.0 / it)
                    }
                }
            }
        }

        // first
        entryPair(
            title = "PDP", index = 0, label1 = "Voltage[V]", label2 = "Current[A]"
        )
        // converter
        singleConverter(title = "Motor Controller", index = 1, label = "DutyCycle")
        // second
        entryPair(
            title = "Motor", index = 1, label1 = "Voltage[V]", label2 = "Current[A]"
        )
        // convert
        div {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                borderColor = rgb(200, 0, 0)
            }
            +"Motor"
            converter(
                label = "kV", state = convertersTop[2]
            )
            converter(
                label = "kT", state = convertersBottom[2]
            )
        }
        // third
        entryPair(
            title = "Motor Shaft", index = 2, label1 = "Velocity[RPM]", label2 = "Torque[N*m]"
        )
        // convert
        singleConverter(
            title = "Gearing", index = 3, label = "Ratio"
        )
        // fourth
        entryPair(
            title = "Output Shaft", index = 3, label1 = "Velocity[RPM]", label2 = "Torque[N*m]"
        )
    }
}
