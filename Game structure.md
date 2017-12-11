# Game structure
This file describes where to find something in the game sources. 
It also describes every file of the game sources.

## Files in package `longHourAndAHalf`
### Actions.kt
Contains the action mechanism - choices which are shown on some slides.

### Bladder.kt
Contains the bladder and sphincter mechanisms.

### Character.kt
Contains the `Character` class which is used for holding the information about the character 
during the gameplay.

### CheatData.kt
Used for managing the cheat usage data.

### Core.kt
Used for the communication between the game classes.
Core is serializable, so saving by writing it into a file is possible.

### CoreHolder.kt
"Hook" for classes for easy access to core.

### CustomLib.kt
Contains some top-level functions for regular usage.

### Game.kt
The game class. Consists of core and UI.

### GameMetadata.kt
Contains the game metadata.

### GameParameters.kt
Used on the game setup to provide the game settings.

### Launcher.kt
Prepares a game for a launch.

### Lesson.kt
Contains the lesson data. Being changed every lesson.

### Plot.kt
Maintains the plot.

### PlotFlags.kt
Way to communicate between plot slides.

### PlotMap.kt
Contains the plot slides.

### PlotStage.kt
The plot slide.

### PlotStageID.kt
Identifiers for the plot slides.

### SaveFileManager.kt
Saves and loads things from/to files.

### SchoolDay.kt
Contains the lesson schedule and classmates data.

### ScoreNomination.kt
The "row in the score table".

### Scorer.kt
Maintains the game score.

### Time.kt
Class for 24-hour time format handling.

### Wardrobe.kt
Contains all wear models.

### Wear.kt
Describes a wear.

### World.kt
Contains some common gameplay data, such as current time.


## Files in package `longHourAndAHalf.ui`

### SaveFileChooser.kt
The save file choosing dialog.

### setupFramePre.kt
The game setup frame.

### StandardGameUI.kt
Official implementation of the `UI` interface.

### UI.kt
The user Interface stub. Use it for creating your own UI.

### WearEditor.kt
Custom wear editor.

### WearFileChooser.kt
The custom wear file choosing dialog.

### MainFrame.java
Future main menu frame.

### SetupFrame.java
Future game setup frame.