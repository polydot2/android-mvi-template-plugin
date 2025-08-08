package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateNewScreenAction : AbstractAction() {
    override val title = "Enter screen Name (e.g., Home):"
    override val CTA = "Create new screen"
    override var files = listOf<Pair<String, String>>(
        "Screen.mustache" to "Screen.kt",
        "ViewModel.mustache" to "ViewModel.kt",
        "ScreenState.mustache" to "State.kt",
        "Event.mustache" to "Events.kt",
        "EventFromVM.mustache" to "SideEffect.kt"
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }
}