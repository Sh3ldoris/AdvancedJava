package cz.cuni.mff.testingframework.tests;

import cz.cuni.mff.testingframework.anotation.After;
import cz.cuni.mff.testingframework.anotation.Before;
import cz.cuni.mff.testingframework.anotation.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TestExample {
    private Collection<String> collection;

    @Before
    public void setUp() {
        collection = new ArrayList<>();
    }

    @After
    public void tearDown() {
        collection.clear();
    }

    @Test
    public void testEmptyCollection() {
        if (!collection.isEmpty())
            throw new AssertionError();
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testOneItemCollection() {
        collection.add("itemA");
        if (1 != collection.size())
            throw new AssertionError();
    }
}
