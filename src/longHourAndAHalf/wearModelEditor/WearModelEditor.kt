package longHourAndAHalf.wearModelEditor

import longHourAndAHalf.*
import javax.swing.*

/**
 * Frame that allows user to create and edit custom [wear models][WearModel].
 * @author JavaBird
 */
class WearModelEditor : JFrame("Wear model editor") {
    // Model name fields
    private val nameField = JTextField("Name", 30)
    private val inGameNameField = JTextField("In-game name", 15)

    // Model parameters' spinners
    private val pressureSpinner = JSpinner(SpinnerNumberModel(0.0, 0.0, 100.0, 1.0))
    private val absorptionSpinner = JSpinner(SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 1.0))
    private val dryingSpinner = JSpinner(SpinnerNumberModel(0.0, 0.0, 100.0, 1.0))

    // Model type switches
    private val underwearRadio = JRadioButton("Underwear", true)
    private val outerwearRadio = JRadioButton("Outerwear")

    private val saveButtonActionListener = {
        val name = nameField.text
        val inGameName = inGameNameField.text
        val pressure = pressureSpinner.value as Double
        val absorption = absorptionSpinner.value as Double
        val drying = dryingSpinner.value as Double

        saveModel(name, inGameName, pressure, absorption, drying)
    }

    private fun saveModel(name: String, inGameName: String, pressure: Double, absorption: Double, drying: Double) {
        if (underwearRadio.isSelected) {
            // Saving underwear
            val model = UnderwearModel(name, inGameName, pressure, absorption, drying)
            UnderwearModelFileChooser().saveObject(model, this)
        } else {
            // Saving outerwear
            val model = OuterwearModel(name, inGameName, pressure, absorption, drying)
            OuterwearModelFileChooser().saveObject(model, this)
        }
    }

    private fun <T : WearModel> openButtonActionListener(fileChooser: SimpleFileChooser<T>) {
        val model = fileChooser.askForObject() ?: return
        loadModel(model, fileChooser)
    }

    private fun <T : WearModel> loadModel(model: T, fileChooser: SimpleFileChooser<T>) {
        nameField.text = model.name
        inGameNameField.text = model.inGameName
        pressureSpinner.value = model.pressure
        absorptionSpinner.value = model.absorption
        dryingSpinner.value = model.drying

        when (fileChooser) {
            is UnderwearModelFileChooser -> underwearRadio.isSelected = true
            is OuterwearModelFileChooser -> outerwearRadio.isSelected = true
        }
    }

    init {
        contentPane.layout = BoxLayout(contentPane, BoxLayout.Y_AXIS)

        addNameFieldsToFrame()
        addParameterSpinnersToFrame()
        addModelTypeSwitchesToFrame()
        addControlButtonsToFrame()

        display()
    }

    private fun addNameFieldsToFrame() {
        add(nameField)
        add(inGameNameField)
    }

    private fun addControlButtonsToFrame() {
        add(JButton("Save...").apply { addActionListener { saveButtonActionListener() } })
        add(JButton("Open underwear...").apply {
            addActionListener {
                openButtonActionListener(UnderwearModelFileChooser())
            }
        })
        add(JButton("Open outerwear...").apply {
            addActionListener {
                openButtonActionListener(OuterwearModelFileChooser())
            }
        })
    }

    private fun addModelTypeSwitchesToFrame() {
        add(underwearRadio)
        add(outerwearRadio)
    }

    private fun addParameterSpinnersToFrame() {
        add(pressureSpinner)
        add(absorptionSpinner)
        add(dryingSpinner)
    }
}