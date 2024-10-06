package pl.semidude.treeviewbydefault

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.diagnostic.logger

class TreeViewByDefaultAction : AnAction() {

    private val settings by lazy { TreeViewByDefaultSettings.instance }
    private val fixedGridStates = mutableSetOf<String>()
    private val gridsWithListeners = mutableSetOf<AutoExpandingTreeDataGrid>()

    override fun update(event: AnActionEvent) {
        if (settings.pluginEnabled) {
            dataGridFrom(event)
                ?.apply(::fixDataGrid)
                ?.apply(::fixDataGridEverytimeDatabaseRequestFinishes)
        }
    }

    private fun dataGridFrom(event: AnActionEvent): AutoExpandingTreeDataGrid? =
        event.dataGrid?.let(::AutoExpandingTreeDataGrid)

    private fun fixDataGrid(dataGrid: AutoExpandingTreeDataGrid) {
        val gridStateHash = dataGrid.stateHash()

        if (gridStateHash !in fixedGridStates) {
            logger.info("Fixing data grid $gridStateHash")
            dataGrid.switchToTreeAndAutoExpand()
            fixedGridStates.add(gridStateHash)
        }
    }

    private fun fixDataGridEverytimeDatabaseRequestFinishes(dataGrid: AutoExpandingTreeDataGrid) {
        if (dataGrid !in gridsWithListeners) {
            logger.info("Adding listener to data grid ${dataGrid.hashCode()}")
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