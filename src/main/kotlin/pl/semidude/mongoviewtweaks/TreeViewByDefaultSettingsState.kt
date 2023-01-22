package pl.semidude.mongoviewtweaks

import com.intellij.database.datagrid.GridPresentationMode
import com.intellij.database.datagrid.GridPresentationMode.TREE_TABLE
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(
    name = "org.intellij.sdk.settings.AppSettingsState",
    storages = [Storage("TreeViewByDefault.xml")]
)
class TreeViewByDefaultSettingsState : PersistentStateComponent<TreeViewByDefaultSettingsState> {

    var defaultPresentationMode: GridPresentationMode = TREE_TABLE
    var autoExpandEnabled: Boolean = true

    override fun getState() = this

    override fun loadState(state: TreeViewByDefaultSettingsState) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        val instance: TreeViewByDefaultSettingsState get() =
            ApplicationManager.getApplication().getService(TreeViewByDefaultSettingsState::class.java)
    }
}