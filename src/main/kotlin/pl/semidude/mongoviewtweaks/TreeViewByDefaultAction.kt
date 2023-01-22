@file:Suppress("UnstableApiUsage")

package pl.semidude.mongoviewtweaks

import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.database.datagrid.GridPresentationMode.TREE_TABLE
import com.intellij.database.run.ui.treetable.GridTreeTable
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.options.ShowSettingsUtil

class TreeViewByDefaultAction : AnAction() {

    private val settings get() = TreeViewByDefaultSettingsState.instance
    private val processedGridStates = mutableSetOf<String>()
    private val gridsWithListeners = mutableSetOf<DataGrid>()

    override fun actionPerformed(e: AnActionEvent) {
        ShowSettingsUtil.getInstance().showSettingsDialog(
            e.project,
            TreeViewByDefaultSettingsConfigurable::class.java
        )
    }

    override fun update(event: AnActionEvent) {
        dataGridFrom(event)
            ?.apply(::forceDefaultsIfNeeded)
            ?.apply(::addListenerIfNeeded)
    }

    private fun dataGridFrom(event: AnActionEvent): DataGrid? =
        DataGridUtil.getDataGrid(event.dataContext)

    private fun forceDefaultsIfNeeded(dataGrid: DataGrid) {
        val gridStateHash = stateHashOf(dataGrid)

        if (settings.defaultPresentationMode == TREE_TABLE && gridStateIsNotYetProcessed(gridStateHash)) {
            logProcessingGrid(dataGrid)
            switchToTreeMode(dataGrid)
            expandIfSingleRow(dataGrid)
            markGridStateAsProcessed(gridStateHash)
        }
    }

    private fun gridStateIsNotYetProcessed(gridStateHash: String): Boolean =
        !processedGridStates.contains(gridStateHash)

    private fun logProcessingGrid(dataGrid: DataGrid) {
        logger<TreeViewByDefaultAction>().warn("Processing ${stateHashOf(dataGrid)}")
    }

    private fun switchToTreeMode(dataGrid: DataGrid) {
        dataGrid.presentationMode = settings.defaultPresentationMode
    }

    private fun expandIfSingleRow(dataGrid: DataGrid) {
        if (settings.autoExpandEnabled) {
            (dataGrid.resultView.component as? GridTreeTable)
                ?.tree
                ?.takeIf { tree -> tree.rowCount == 1 }
                ?.also { tree -> tree.expandRow(0) }
        }
    }

    private fun addListenerIfNeeded(dataGrid: DataGrid) {
        if (dataGrid !in gridsWithListeners) {
            dataGrid.addRequestFinishedListener { forceDefaultsIfNeeded(dataGrid) }
            gridsWithListeners.add(dataGrid)
        }
    }

    private fun markGridStateAsProcessed(gridStateHash: String) {
        processedGridStates.add(gridStateHash)
    }

    private fun stateHashOf(dataGrid: DataGrid) =
        dataGrid.hashCode().toString() + "_" + hashOfFirstRow(dataGrid)

    private fun hashOfFirstRow(dataGrid: DataGrid) =
        (dataGrid.resultView.component as? GridTreeTable)
            ?.tree
            ?.getPathForRow(0)
            ?.hashCode()
}