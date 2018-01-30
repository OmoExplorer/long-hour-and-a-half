package longHourAndAHalf

import java.awt.Color
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.border.Border
import javax.swing.border.EtchedBorder

/**
 * Panel for picking a color for wear.
 *
 * **Usage:**
 *
 * Used in [GameSetupFrame] for picking a wear color.
 *
 * @author JavaBird
 */
class ColorPicker : JPanel() {
    /**
     * Colored square panel that represents one color choice.
     * When clicked, calls [onColorPanelSelected] in [ColorPicker].
     *
     * @author JavaBird
     */
    private inner class ColorPanel(val color: WearColor) : JPanel() {
        var selected = false
            set(value) {
                field = value

                with(ColorPanelConfig.Borders) { border = if (selected) ENABLED else DISABLED }
            }

        init {
            setUpAppearance(color)

            addMouseListener(object : MouseListener {
                override fun mouseClicked(e: MouseEvent?) {
                    onColorPanelSelected(this@ColorPanel)
                }

                override fun mouseReleased(e: MouseEvent?) {}
                override fun mouseEntered(e: MouseEvent?) {}
                override fun mouseExited(e: MouseEvent?) {}
                override fun mousePressed(e: MouseEvent?) {}
            })
        }

        private fun setUpAppearance(color: WearColor) {
            border = ColorPanelConfig.Borders.DISABLED
            with(ColorPanelConfig) { setSize(SIZE, SIZE) }
            background = color.awtColor
            toolTipText = color.name.toLowerCase().capitalize()
        }
    }

    /** Currently selected color. */
    var selectedColor = WearColor.RANDOM
        private set

    private val colorPanels = WearColor.values().map { ColorPanel(it) }

    init {
        colorPanels[0].selected = true
    }

    private fun onColorPanelSelected(selectedPanel: ColorPanel) {
        selectedPanel.selected = true
        deselectOtherPanels(selectedPanel)
        selectedColor = selectedPanel.color
    }

    private fun deselectOtherPanels(selectedPanel: ColorPanel) {
        colorPanels.filter { it != selectedPanel }.forEach { it.selected = false }
    }

    private object ColorPanelConfig {
        const val SIZE = 15

        object Borders {
            val ENABLED: Border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.BLACK, Color.BLACK)
            val DISABLED: Border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.BLACK)
        }
    }
}