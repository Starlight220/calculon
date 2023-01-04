import csstype.*
import emotion.react.css
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input

external interface EntryProp : Props {
    var label: String
    var value: Double
    var set: (Double) -> Unit
}

fun ChildrenBuilder.entry(
    label: String,
    state: StateInstance<Double>,
    k: Double
) {
    val (value, setter) = state
    Entry {
        this.label = label
        this.value = value * k
        this.set = { setter(it / k) }
    }
}

fun ChildrenBuilder.converter(
    label: String,
    state: StateInstance<Double>
) {
    val (value, setter) = state
    Converter {
        this.label = label
        this.value = value
        this.set = { setter(it) }
    }
}

val Entry = field {
    backgroundColor = rgb(8, 97, 22)
    color = rgb(56, 246, 137)
}

val Converter = field {
    backgroundColor = rgb(80, 9, 22)
    color = rgb(156, 26, 37)
}

private fun field(css: PropertiesBuilder.() -> Unit) = FC<EntryProp> { props ->
    div {
        css {
            css()
            display = Display.flex
            flexDirection = FlexDirection.row
            padding = 5.px
        }
        +"${props.label}:"
        input {
            css {
                marginTop = 5.px
                marginBottom = 5.px
                fontSize = 14.px
                width = 5.em
            }
            type = InputType.number
            value = (props.value.takeUnless(Double::isInfinite) ?: 0.0).toString()
                .also { println("> $it") }
            onChange = onChange@{ event ->
                event.target.value.takeUnless(String::isBlank)?.let {
                    println(it)
                    props.set(it.toDouble())
                }
            }
        }
    }
}
