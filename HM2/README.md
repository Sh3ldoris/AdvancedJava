# HW 2 - Class annotations processor
Project represents processor which takes class and writes out information
about every annotated method that is not abstract or a part ot `java.lang.Object`.
The name of the class is taken from program argument.

### Example
For class:
```java
public class AClass {
    @CodExAnnotation
    private void foo(int a) {}

    public int bar() {
        return 1;
    }
}
```
The output should looks like:
```
Annotations in "AClass":
@CodExAnnotation foo()
```
