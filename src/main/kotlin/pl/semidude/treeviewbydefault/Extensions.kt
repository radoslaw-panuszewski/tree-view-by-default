package pl.semidude.treeviewbydefault

import com.intellij.database.connection.throwable.info.ErrorInfo
import com.intellij.database.datagrid.DataConsumer
import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.database.datagrid.GridDataHookUp
import com.intellij.database.datagrid.GridRequestSource
import com.intellij.database.run.ui.treetable.GridTreeTable
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.ui.treeStructure.Tree

val AnActionEvent.dataGrid: DataGrid?
    get() = DataGridUtil.getDataGrid(dataContext)

val DataGrid.tree: Tree?
    get() = (resultView.component as? GridTreeTable)?.tree

fun DataGrid.addRequestFinishedListener(listener: () -> Unit) {
    dataHookup.addRequestListener(object : GridDataHookUp.RequestListener<DataConsumer.Row, DataConsumer.Column> {
        override fun error(var1: GridRequestSource, var2: ErrorInfo) {
            // ignore
        }

        override fun updateCountReceived(var1: GridRequestSource, var2: Int) {
            // ignore
        }

        override fun requestFinished(var1: GridRequestSource, var2: Boolean) {
            listener()
        }
    }, {})
}