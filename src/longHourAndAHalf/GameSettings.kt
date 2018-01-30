package longHourAndAHalf

object GameSettings {
    fun createUI(gameplay: Gameplay): UI = ConsoleUI(gameplay)
    fun createPlot(gameplay: Gameplay): Plot = ALongHourAndAHalfPlot(gameplay)
}