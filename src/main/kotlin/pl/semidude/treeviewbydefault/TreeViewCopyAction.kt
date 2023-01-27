package pl.semidude.treeviewbydefault

import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.database.run.ui.treetable.GridTreeTable
import com.intellij.database.run.ui.treetable.ValueWrapper
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ide.CopyPasteManager
import java.awt.datatransfer.StringSelection

class TreeViewCopyAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val dataGrid = dataGridFrom(event)
        val selected = (dataGrid.resultView.component as? GridTreeTable)?.tree?.lastSelectedPathComponent
        val wrapperField = selected?.javaClass?.declaredFields?.find { it.name == "wrapper" }
        wrapperField?.isAccessible = true
        val wrapper = wrapperField?.get(selected) as? ValueWrapper<*>
        CopyPasteManager.getInstance().setContents(StringSelection(wrapper?.getValue().toString()))
    }

    private fun dataGridFrom(event: AnActionEvent): DataGrid =
        DataGridUtil.getDataGrid(event.dataContext)!!
}