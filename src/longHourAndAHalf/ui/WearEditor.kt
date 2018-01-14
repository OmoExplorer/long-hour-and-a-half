package longHourAndAHalf.ui

import longHourAndAHalf.OuterwearModel
import longHourAndAHalf.SaveFileManager
import longHourAndAHalf.UnderwearModel
import longHourAndAHalf.WearModel
import java.awt.IllegalComponentStateException
import javax.swing.*
import javax.swing.GroupLayout.Alignment
import javax.swing.LayoutStyle.ComponentPlacement

class WearEditor : JFrame("Wear editor") {
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private var nameLabel = JLabel("Wear name")
    private var nameField = JTextField()
    private var typeLabel = JLabel()
    private var typeComboBox = JComboBox<String>()
    private var pressureLabel = JLabel()
    private var pressureSpinner = JSpinner()
    private var absorptionLabel = JLabel()
    private var absorptionSpinner = JSpinner()
    private var dotLabel = JLabel()
    private var dotSpinner = JSpinner()
    private var saveButton = JButton()
    private var openButton = JButton()
    private var insertNameLabel = JLabel()
    private var insertNameField = JTextField()

    init {
        initComponents()
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private fun initComponents() {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE

        val nameTooltip = "Your wear name (e. g. \"Beautiful skirt\")"
        nameLabel.toolTipText = nameTooltip
        nameField.toolTipText = nameTooltip

        typeLabel.text = "Wear type"
        typeLabel.toolTipText = "Type of your wear: undies or lower."

        typeComboBox.model = DefaultComboBoxModel(arrayOf("Underwear", "Outerwear", "Both are suitable"))
        typeComboBox.selectedIndex = 2

        val pressureTooltip = "<html>\nDecreases the maximal bladder capacity.<br>\n<b>1 point = -1% of max. bladder capacity.</b>\n</html>"
        pressureLabel.text = "Pressure"
        pressureLabel.toolTipText = pressureTooltip

        pressureSpinner.model = SpinnerNumberModel(0, 0, 300, 1)
        pressureSpinner.toolTipText = pressureTooltip

        absorptionLabel.text = "Absorption"
        absorptionLabel.toolTipText = "<html>\nAbsorbs the leaked pee.<br>\n<b>1 point = 0.5% of pee.</b>\n</html>\n"

        absorptionSpinner.model = SpinnerNumberModel(0, 0, 300, 1)
        absorptionSpinner.toolTipText = "<html> Absorbs the leaked pee.<br> <b>1 point = 0.5% of pee.</b> </html> "

        dotLabel.text = "Drying over time"
        dotLabel.toolTipText = "<html>\nSpeed of wear drying.<br>\n<b>1 point = -1% of absorbed pee per 3 minutes.</b>\n</html>"
        dotLabel.name = "dotLabel"

        dotSpinner.model = SpinnerNumberModel(0, 0, 100, 1)
        dotSpinner.toolTipText = "<html> Speed of wear drying.<br> <b>1 point = -1% of absorbed pee per 3 minutes.</b> </html>"
        dotSpinner.name = "dotSpinner"

        saveButton.text = "Save..."
        saveButton.name = "saveButton"
        saveButton.addActionListener { saveButtonActionPerformed() }

        openButton.text = "Open..."
        openButton.name = "openButton"
        openButton.addActionListener { openButtonActionPerformed() }

        insertNameLabel.text = "Wear insert name"
        insertNameLabel.toolTipText = "Your wear name which is inserted in the game name (e. g. \"skirt\")"
        insertNameLabel.name = "insertNameLabel"

        insertNameField.toolTipText = "Your wear name which is inserted in the game name (e. g. \"skirt\")"
        insertNameField.name = "insertNameField"

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(nameLabel)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(insertNameLabel)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(insertNameField)
                                        .addContainerGap())
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(typeLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(typeComboBox, 0, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))
                                .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, java.lang.Short.MAX_VALUE.toInt())
                                        .addComponent(pressureLabel)
                                        .addGap(18, 18, 18)
                                        .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                        .addComponent(absorptionLabel)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.UNRELATED)
                                        .addComponent(dotLabel)
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt())
                                        .addComponent(openButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))))
        )
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(nameLabel)
                                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(insertNameLabel)
                                .addComponent(insertNameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(typeLabel)
                                .addComponent(typeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(pressureLabel)
                                .addComponent(pressureSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(absorptionLabel)
                                .addComponent(absorptionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(dotLabel)
                                .addComponent(dotSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(saveButton)
                                .addComponent(openButton))
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, java.lang.Short.MAX_VALUE.toInt()))
        )

        pack()
    }

    private val JSpinner.doubleValue
        get() = (value as Float).toDouble()

    private fun saveButtonActionPerformed() {
        val name = nameField.text
        val insertName = nameField.text.let { if (it.isBlank()) name else it }
        val pressure = pressureSpinner.doubleValue
        val absorption = absorptionSpinner.doubleValue
        val dryingOverTime = dotSpinner.doubleValue

        val fileChooser = when (typeComboBox.selectedIndex) {
            0 -> UnderwearFileChooser()
            1 -> OuterwearFileChooser()
            else -> throw IllegalComponentStateException()
        }

        val wear = when (typeComboBox.selectedIndex) {
            0 -> UnderwearModel(name, insertName, pressure, absorption, dryingOverTime, true)
            1 -> OuterwearModel(name, insertName, pressure, absorption, dryingOverTime, true)
            else -> throw IllegalComponentStateException()
        }

        SaveFileManager.writeFile(fileChooser, wear, "lhhwear")
    }

    private fun openButtonActionPerformed() {
        val wear = SaveFileManager.openFile<WearModel>(WearFileChooser())

        wear?.let {
            nameField.text = wear.name
            insertNameField.text = wear.insertName
            pressureSpinner.value = wear.pressure
            absorptionSpinner.value = wear.absorption
            dotSpinner.value = wear.dryingOverTime

            typeComboBox.selectedIndex = when (wear) {
                is UnderwearModel -> 0
                is OuterwearModel -> 1
                else -> throw IllegalArgumentException()
            }
        }
    }
}
