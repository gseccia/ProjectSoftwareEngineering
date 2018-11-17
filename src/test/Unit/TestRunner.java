package test.Unit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.Unit.elements.MobTest;

public class TestRunner {

    public static void main(String[] args){
        Result mobtest = JUnitCore.runClasses(MobTest.class);

        for(Failure f : mobtest.getFailures()){
            System.out.println(f.toString());
        }

        System.out.println(mobtest.wasSuccessful());

    }
}
