package pl.semidude.treeviewbydefault

import com.intellij.database.run.ui.treetable.ValueWrapper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection

class TreeViewCopyAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val selectedNode = getSelectedTreeNode(event)
        val value = extractValueFrom(selectedNode)
        putIntoClipboard(value)
    }

    private fun getSelectedTreeNode(event: AnActionEvent) =
        event.dataGrid?.tree?.lastSelectedPathComponent

    private fun extractValueFrom(selected: Any?): Any? {
        val wrapperField = selected?.javaClass?.declaredFields?.find { it.name == "wrapper" }
        wrapperField?.isAccessible = true
        val wrapper = wrapperField?.get(selected) as? ValueWrapper<*>
        return wrapper?.getValue()
    }

    private fun putIntoClipboard(value: Any?) {
        CopyPasteManager.getInstance().setContents(StringSelection(value?.toString()))
    }
}