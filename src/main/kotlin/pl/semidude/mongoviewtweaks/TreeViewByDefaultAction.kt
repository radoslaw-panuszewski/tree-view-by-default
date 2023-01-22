package pl.semidude.mongoviewtweaks

import com.intellij.database.datagrid.DataGrid
import com.intellij.database.datagrid.DataGridUtil
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger

class TreeViewByDefaultAction : AnAction() {

    private val settings by lazy { TreeViewByDefaultSettings.instance }
    private val fixedGridStates = mutableSetOf<String>()
    private val gridsWithListeners = mutableSetOf<DataGrid>()

    override fun update(event: AnActionEvent) {
        if (settings.pluginEnabled) {
            dataGridFrom(event)
                ?.apply(::fixDataGrid)
                ?.apply(::fixDataGridEveryTimeDatabaseRequestFinishes)
        }
    }

    private fun dataGridFrom(event: AnActionEvent): AutoExpandingTreeDataGrid? =
        DataGridUtil.getDataGrid(event.dataContext)
            ?.let(::AutoExpandingTreeDataGrid)

    private fun fixDataGrid(dataGrid: AutoExpandingTreeDataGrid) {
        val gridStateHash = dataGrid.stateHash()

        if (gridStateHash !in fixedGridStates) {
            logger.warn("Fixing data grid $gridStateHash")
            dataGrid.switchToTreeAndAutoExpand()
            fixedGridStates.add(gridStateHash)
        }
    }

    private fun fixDataGridEveryTimeDatabaseRequestFinishes(dataGrid: AutoExpandingTreeDataGrid) {
        if (dataGrid !in gridsWithListeners) {
            dataGrid.addRequestFinishedListener { fixDataGrid(dataGrid) }
            gridsWithListeners.add(dataGrid)
        }
    }

    override fun actionPerformed(e: AnActionEvent) {
        TreeViewByDefaultSettingsConfigurable.openPluginSettings(e.project)
    }

    companion object {
        private val logger = logger<TreeViewByDefaultAction>()
    }
}