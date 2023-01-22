package pl.semidude.mongoviewtweaks

import com.intellij.database.datagrid.GridPresentationMode
import com.intellij.database.datagrid.GridPresentationMode.TABLE
import com.intellij.database.datagrid.GridPresentationMode.TEXT
import com.intellij.database.datagrid.GridPresentationMode.TREE_TABLE
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.ui.ComboBoxWithWidePopup
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class TreeViewByDefaultSettingsConfigurable : Configurable {

    private val settings get() = TreeViewByDefaultSettingsState.instance
    private lateinit var defaultPresentationModeCombo: ComboBoxWithWidePopup<String>
    private lateinit var autoExpandCheckbox: JBCheckBox // TODO make it clickable only if default presentation mode is TREE
    private lateinit var mainPanel: JPanel

    // TODO set the presentation mode also in ViewAs action panel
    override fun createComponent(): JComponent {
        defaultPresentationModeCombo = createCombo()
        autoExpandCheckbox = createCheckbox()
        mainPanel = createMainPanel()
        return mainPanel
    }

    private fun createCombo() =
        ComboBoxWithWidePopup(arrayOf("Table", "Tree")).apply {
            selectedItem = settings.defaultPresentationMode.toComboItem()
        }

    private fun createCheckbox() =
        JBCheckBox("Automatically expand if single row?").apply {
            isSelected = settings.autoExpandEnabled
        }

    private fun createMainPanel() =
        FormBuilder.createFormBuilder()
            .addLabeledComponent("Default presentation mode", defaultPresentationModeCombo)
            .addComponent(autoExpandCheckbox)
            .addComponentFillVertically(JPanel(), 0)
            .panel

    override fun isModified(): Boolean =
        defaultPresentationModeCombo.selectedItem != settings.defaultPresentationMode.toComboItem() ||
            settings.autoExpandEnabled != autoExpandCheckbox.isSelected

    override fun apply() {
        settings.defaultPresentationMode = defaultPresentationModeCombo.selectedItem.toEnum()
        settings.autoExpandEnabled = autoExpandCheckbox.isSelected
    }

    override fun getDisplayName() = "TreeViewByDefault Settings"
}

private fun GridPresentationMode.toComboItem() =
    when (this) {
        TABLE -> "Table"
        TREE_TABLE -> "Tree"
        TEXT -> "Text"
    }

private fun Any?.toEnum(): GridPresentationMode {
    require(this is String)

    return when (this) {
        "Table" -> TABLE
        "Tree" -> TREE_TABLE
        "Text" -> TEXT
        else -> error("Unexpected combo item: $this")
    }
}