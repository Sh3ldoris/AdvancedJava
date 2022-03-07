package cz.cuni.mff.testingframework.model;

import cz.cuni.mff.testingframework.anotation.Priority;
import cz.cuni.mff.testingframework.anotation.TesterInfo;

import java.lang.reflect.Method;
import java.util.List;

public record TestClassInfo(Class<?> clazz,
                            String createdBy,
                            String lastModified,
                            Priority priority,
                            List<Method> beforeMethods,
                            List<Method> testMethods,
                            List<Method> afterMethods) {
    public TestClassInfo(Class<?> clazz,
                  TesterInfo testerInfo,
                  List<Method> beforeMethods,
                  List<Method> testMethods,
                  List<Method> afterMethods) {
        this(
                clazz,
                testerInfo.createdBy(),
                testerInfo.lastModified(),
                testerInfo.priority(),
                beforeMethods,
                testMethods,
                afterMethods
        );
    }
}
