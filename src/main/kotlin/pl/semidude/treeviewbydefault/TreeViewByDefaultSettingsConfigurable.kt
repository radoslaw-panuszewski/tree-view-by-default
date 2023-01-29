package pl.semidude.treeviewbydefault

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ShowSettingsUtil
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class TreeViewByDefaultSettingsConfigurable : Configurable {

    private val settings get() = TreeViewByDefaultSettings.instance
    private lateinit var pluginEnabledCheckbox: JBCheckBox
    private lateinit var autoExpandEnabledCheckbox: JBCheckBox
    private lateinit var mainPanel: JPanel

    override fun createComponent(): JComponent {
        pluginEnabledCheckbox = createPluginEnabledCheckbox()
        autoExpandEnabledCheckbox = createAutoExpandEnabledCheckbox()
        mainPanel = createMainPanel()
        return mainPanel
    }

    private fun createPluginEnabledCheckbox() =
        JBCheckBox("Automatically switch to tree mode on every database request").apply {
            isSelected = settings.pluginEnabled
            addChangeListener {
                autoExpandEnabledCheckbox.isEnabled = pluginEnabledCheckbox.isSelected
            }
        }

    private fun createAutoExpandEnabledCheckbox() =
        JBCheckBox("Automatically expand if single row").apply {
            isSelected = settings.autoExpandEnabled
            isEnabled = pluginEnabledCheckbox.isSelected
        }

    private fun createMainPanel() =
        FormBuilder.createFormBuilder()
            .addComponent(pluginEnabledCheckbox)
            .addComponent(autoExpandEnabledCheckbox)
            .addComponentFillVertically(JPanel(), 0)
            .panel

    override fun isModified(): Boolean =
        settings.pluginEnabled != pluginEnabledCheckbox.isSelected ||
            settings.autoExpandEnabled != autoExpandEnabledCheckbox.isSelected

    override fun apply() {
        settings.pluginEnabled = pluginEnabledCheckbox.isSelected
        settings.autoExpandEnabled = autoExpandEnabledCheckbox.isSelected
    }

    override fun getDisplayName() = "TreeViewByDefault Settings"

    companion object {
        fun openPluginSettings(project: Project?) {
            ShowSettingsUtil
                .getInstance()
                .showSettingsDialog(project, TreeViewByDefaultSettingsConfigurable::class.java)
        }
    }
}