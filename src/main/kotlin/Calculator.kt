package io.github.starlight220.robocalc

import csstype.*
import emotion.react.css
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.br
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.hr
import react.useState

val Calculator = FC<Props> {
    val stateTop = useState(0.0)
    val convertersTop = doubleArrayOf(1.0, 0.0, 0.0, 0.0).map { useState(it) }
    val stateBottom = useState(0.0)
    val convertersBottom = doubleArrayOf(1.0, 0.0, 0.0, 0.0).map { useState(it) }

    div {
        css {
            textAlign = TextAlign.center
            display = Display.flex
            justifyContent = JustifyContent.spaceAround
            flexDirection = FlexDirection.row
            alignSelf = AlignSelf.stretch
            alignContent = AlignContent.stretch
            alignItems = AlignItems.stretch
        }
        fun entryPair(title: String, index: Int, label1: String, label2: String) {
            ReactHTML.div {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    border = Border(2.px, LineStyle.solid, rgb(0,0,0))
                    justifyContent = JustifyContent.center
                    padding = Padding(2.px, 2.px)
                    backgroundColor = rgb(8, 97, 22)
                    color = rgb(56, 246, 137)
                }
                +title
                hr()
                entry(
                    state = stateTop, label = label1, k = convertersTop.fold(index)
                )
                hr()
                entry(
                    state = stateBottom, label = label2, k = convertersBottom.fold(index)
                )
            }
        }

        fun singleConverter(title: String, index: Int, label: String) {
            ReactHTML.div {
                css {
                    display = Display.flex
                    flexDirection = FlexDirection.column
                    border = Border(2.px, LineStyle.solid, rgb(0,0,0))
                    justifyContent = JustifyContent.center
                    padding = Padding(2.px, 2.px)
                    backgroundColor = rgb(80, 9, 22)
                    color = rgb(156, 26, 37)
                }
                +title
                hr()
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
        ReactHTML.div {
            css {
                display = Display.flex
                flexDirection = FlexDirection.column
                border = Border(2.px, LineStyle.solid, rgb(0,0,0))
                justifyContent = JustifyContent.center
                padding = Padding(2.px, 2.px)
                backgroundColor = rgb(80, 9, 22)
                color = rgb(156, 26, 37)
            }
            +"Motor"
            hr()
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
