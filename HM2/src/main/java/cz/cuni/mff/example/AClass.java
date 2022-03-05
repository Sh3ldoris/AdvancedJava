package cz.cuni.mff.example;

import cz.cuni.mff.annotation.CodExAnnotation;
import cz.cuni.mff.annotation.Examppp;

public class AClass {
    @Examppp
    @CodExAnnotation
    private void foo(int a) {}

    public int bar() {
        return 1;
    }
}

