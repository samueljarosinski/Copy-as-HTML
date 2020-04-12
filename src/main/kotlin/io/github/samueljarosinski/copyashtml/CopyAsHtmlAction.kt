package io.github.samueljarosinski.copyashtml

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class CopyAsHtmlAction : AnAction() {

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible =
            event.getData(CommonDataKeys.EDITOR)?.selectionModel?.hasSelection() ?: false
    }

    override fun actionPerformed(event: AnActionEvent) {

    }
}
