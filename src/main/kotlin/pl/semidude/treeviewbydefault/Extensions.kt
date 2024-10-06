package pl.semidude.treeviewbydefault

import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.database.run.ui.treetable.GridTreeTable
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.ui.treeStructure.Tree

val AnActionEvent.dataGrid: DataGrid?
    get() = DataGridUtil.getDataGrid(dataContext)

val DataGrid.tree: Tree?
    get() = (resultView.component as? GridTreeTable)?.tree