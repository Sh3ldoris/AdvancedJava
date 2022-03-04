# Lab 1 - Reflection API
This project is example of using Java reflection API to load 
specific implementations of certain "plugin" interface.
To tell the program which implementation should be loaded is done
by simply passing name of main.java.text file as argument of the program.

### [Command example](src/shell/command)
"Extensible main.java.shell" which takes user input from command line and try to execute
pre-defined command on that input. Then output is printed on the same command line.
* __Help__ - default always there command for printing info about other loaded commands
* __Echo__ - basich echo commad that repeates input main.java.text on output

### [Text processor](src/text/processor)
Input main.java.text processor which takes user input and process it with loaded processors and print it to the output.

### [Utils](src/utils)
Package contains util class which implementing shared functionality between examples.

