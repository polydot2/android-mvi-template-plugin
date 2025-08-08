package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateNewScreenAction : AbstractAction() {
    override val title = "Enter screen Name (e.g., Home):"
    override val CTA = "Create new screen"
    override var files = listOf<Pair<String, String>>(
        "ComposableScreen.mustache" to "Screen.kt",
        "ViewModel.mustache" to "ViewModel.kt",
        "State.mustache" to "State.kt",
        "Events.mustache" to "Events.kt",
        "SideEffect.mustache" to "SideEffect.kt"
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }
}