package com.poly.helpme

import com.intellij.openapi.actionSystem.AnActionEvent

class CreateUseCasesAction : AbstractAction() {
    override val title = "Enter repository Name (e.g., MyRepo):"
    override val CTA = "Create 3 usecases"
    override var files = listOf<Pair<String, String>>(
        "ObserveUsecase.mustache" to "ObserveUseCase.kt",
        "GetUsecase.mustache" to "GetUseCase.kt",
        "SaveUsecase.mustache" to "SaveUseCase.kt",
    )

    override fun actionPerformed(event: AnActionEvent) {
        super.actionPerformed(event)
    }

    override fun fileName(mustache: String, name: String, suffixeFile: String): String {
        return when (mustache) {
            "ObserveUsecase.mustache" -> "Observe$name$suffixeFile"
            "GetUsecase.mustache" -> "Get$name$suffixeFile"
            "SaveUsecase.mustache" -> "Save$name$suffixeFile"
            else -> "$name$suffixeFile"
        }
    }
}