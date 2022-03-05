# HW 3 - Directory service loader
This multimodule project represents simple text processor which takes input from user,
process it with loaded textprocessor plugins and then print it to the output.
Program loads plugin packaged in .jar files which are in given directory. The directory is passed by program argument.
To load plugins is used Java ServiceLoader.

Project consists of multiple modules:
### [Core](Core)
The core module holds information about [`TextProcessor`](Core/src/main/java/cz/cuni/mff/java/plugins/textprocessor/TextProcessor.java) interface and also here is
implemented logic of loading user input and its processing.

### [DotsToExclamationsPlugin](DotsToExclamationsPlugin)
This is specific implementation of [`TextProcessor`](Core/src/main/java/cz/cuni/mff/java/plugins/textprocessor/TextProcessor.java) interface.
It looks for every dot in string and changes them with exclamations. 

### [ToUpperCasePlugin](ToUpperCasePlugin)
Implementation of [`TextProcessor`](Core/src/main/java/cz/cuni/mff/java/plugins/textprocessor/TextProcessor.java) interface 
which takes characters in string and change them to the upper case.
