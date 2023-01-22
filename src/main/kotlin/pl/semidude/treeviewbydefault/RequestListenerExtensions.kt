package pl.semidude.treeviewbydefault

import com.intellij.database.connection.throwable.info.ErrorInfo
import com.intellij.database.datagrid.DataConsumer.Column
import com.intellij.database.datagrid.DataConsumer.Row
import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.GridDataHookUp.RequestListener
import com.intellij.database.datagrid.GridRequestSource

fun DataGrid.addRequestFinishedListener(listener: () -> Unit) {
    dataHookup.addRequestListener(object : RequestListener<Row, Column> {
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