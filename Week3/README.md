# Lab 3 - Advanced text processor
Project is updated version of [___Week1___](./Week1) project but in this version 
to load [`textprocessor`](Core/src/main/java/cz/cuni/mff/textprocessor/processor/TextProcessor.java)
plugins is used `java.util.ServiceLoader`. There are 2 implementation of loading pluggins:

### Classpath ServiceLoader
To load plugins from classpath project has to be executed with all plugins classpaths of plugin implementations.

Executing from Windows commandline e.g.
```
java -cp 'Core/target/Core-1.0-SNAPSHOT.jar;ToUpper/target/ToUpperCaseProcessor-1.0-SNAPSHOT.jar' cz.cuni.mff.textprocessor.AdvancedTextProcessorMain
```

### Remote url ServiceLoader
To load plugins from remote URL where are implementations (`.jar`)
use program arguments to pass file location where are URLs stored.

