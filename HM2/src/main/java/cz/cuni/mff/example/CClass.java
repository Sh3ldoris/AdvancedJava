package cz.cuni.mff.example;

import cz.cuni.mff.annotation.CodExAnnotation;
import cz.cuni.mff.annotation.Examppp;

public class CClass extends BClass {
    @CodExAnnotation
    private void cFoo(int a) {
    }

    @Override
    @Examppp
    public int bexampleMethod() {
        return super.bexampleMethod();
    }
}
