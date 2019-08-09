package me.jessyan.mvparms.demo.base;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * @author DrChen
 * @Date 2019/8/9 0009.
 * qq:1414355045
 */
public class MyPresenterRunner extends RobolectricTestRunner {

    /**
     * Creates a runner to run {@code testClass}. Looks in your working directory for your AndroidManifest.xml file
     * and res directory by default. Use the {@link Config} annotation to configure.
     *
     * @param testClass the test class to be run
     * @throws InitializationError if junit says so
     */
    public MyPresenterRunner(Class<?> testClass) throws InitializationError {
        super(testClass);
    }
}
