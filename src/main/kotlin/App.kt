package io.github.starlight220.robocalc

import csstype.*
import emotion.react.css
import kotlinx.browser.document
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.dom.html.ReactHTML.a
import react.dom.html.ReactHTML.div

fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    createRoot(container).render(App.create {})
}

val App = FC<Props> {
    div {
        css {
            backgroundColor = rgb(87, 179, 255)
            alignSelf = AlignSelf.stretch
        }
        Calculator()
        GithubLink()
    }
}


val GithubLink =  FC<Props> {
    a {
        href = "https://github.com/Starlight220/robocalc/"
        className = ClassName("fab fa-github")
        id = "github"
    }
}
