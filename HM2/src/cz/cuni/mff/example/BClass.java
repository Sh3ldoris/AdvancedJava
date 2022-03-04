package cz.cuni.mff.example;

import cz.cuni.mff.annotation.CodExAnnotation;

public class BClass extends AClass {
    @CodExAnnotation
    private void bFoo(int a) {
    }

    @CodExAnnotation
    public int bexampleMethod() {
        return 1;
    }
}
