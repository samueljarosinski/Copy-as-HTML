package io.github.samueljarosinski.copyashtml

import com.intellij.codeInsight.editorActions.TextBlockTransferable
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.IdeActions
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.util.ui.TextTransferable

class CopyAsHtmlAction : AnAction() {

    override fun update(event: AnActionEvent) {
        event.presentation.isEnabledAndVisible =
            event.getData(CommonDataKeys.EDITOR)?.selectionModel?.hasSelection() ?: false
    }

    override fun actionPerformed(event: AnActionEvent) {
        ActionManager.getInstance().getAction(IdeActions.ACTION_EDITOR_COPY).actionPerformed(event)

        val copyPasteManager = CopyPasteManager.getInstance()

        val textBlockTransferable = copyPasteManager.allContents
            .filterIsInstance<TextBlockTransferable>()
            .firstOrNull() ?: return

        val dataFlavor = textBlockTransferable.transferDataFlavors
            .firstOrNull { it.isMimeTypeEqual("text/html") } ?: return

        dataFlavor.getReaderForText(textBlockTransferable).useLines { lines ->
            copyPasteManager.setContents(TextTransferable(lines.joinToString(separator = "")))
        }
    }
}
