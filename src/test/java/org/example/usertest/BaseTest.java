package org.example.usertest;

import org.example.utils.LogRolloverManager;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

public class BaseTest {
    protected SoftAssert softAssert = new SoftAssert();

    @BeforeSuite
    public void beforeSuite() {
        LogRolloverManager.executeLogRollover();
    }
}
