@file:Suppress("UnstableApiUsage")

package pl.semidude.treeviewbydefault

import com.intellij.database.connection.throwable.info.ErrorInfo
import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.GridColumn
import com.intellij.database.datagrid.GridDataHookUp.RequestListener
import com.intellij.database.datagrid.GridPresentationMode.TREE_TABLE
import com.intellij.database.datagrid.GridRequestSource
import com.intellij.database.datagrid.GridRow

class AutoExpandingTreeDataGrid(
    val dataGrid: DataGrid
) {
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

    fun addRequestFinishedListener(listener: () -> Unit) {
        dataGrid.dataHookup.addRequestListener(object : RequestListener<GridRow, GridColumn> {
            override fun error(var1: GridRequestSource, var2: ErrorInfo) {
                // ignore
            }

            override fun updateCountReceived(var1: GridRequestSource, var2: Int) {
                // ignore
            }

            override fun requestFinished(var1: GridRequestSource, var2: Boolean) {
                listener()
            }
        }) {}
    }

    override fun equals(other: Any?): Boolean =
        dataGrid == (other as? AutoExpandingTreeDataGrid)?.dataGrid

    override fun hashCode(): Int =
        dataGrid.hashCode()
}