@file:Suppress("UnstableApiUsage")

package pl.semidude.treeviewbydefault

import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.GridPresentationMode.TREE_TABLE
import com.intellij.database.run.ui.treetable.GridTreeTable

class AutoExpandingTreeDataGrid(private val dataGrid: DataGrid) : DataGrid by dataGrid {

    private val settings by lazy { TreeViewByDefaultSettings.instance }

    fun switchToTreeAndAutoExpand() {
        switchToTreeMode()
        if (settings.autoExpandEnabled) {
            expandIfSingleRow()
        }
    }

    private fun switchToTreeMode() {
        dataGrid.presentationMode = TREE_TABLE
    }

    private fun expandIfSingleRow() {
        dataGrid.tree
            ?.takeIf { tree -> tree.rowCount == 1 }
            ?.also { tree -> tree.expandRow(0) }
    }

    fun stateHash() =
        dataGrid.hashCode().toString() + "_" + hashOfFirstRow()

    private fun hashOfFirstRow() =
        dataGrid.tree?.getPathForRow(0)?.hashCode()
}