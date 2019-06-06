package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Cadmy on 06.06.2019.
 */
public class SolverTest {

    @Test
    public void checkCorrectScenario() {
        Solver solver = new Solver(
                "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        try {
            String result = solver.solve();
            assertEquals("wrong calculation", "4", result);
        } catch(APIException e){
            fail("error while process task data");
        }
    }

    @Test(expected = APIException.class)
    public void checkMoreThan15ItemsIsNotAccepted() throws APIException {
        Solver solver = new Solver(
                "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48) " +
                        "(7,90.72,€13) (8,33.80,€40) (9,43.15,€10) (10,37.97,€16) (11,46.81,€36) (12,48.77,€79) " +
                        "(13,81.80,€45) (14,19.36,€79) (15,6.76,€64) (16,6.76,€64)");

        String result = solver.solve();
    }

    @Test(expected = APIException.class)
    public void checkMaxWeightIsNotMoreThan100() throws APIException {
        Solver solver = new Solver(
                "101 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        String result = solver.solve();
    }

    @Test
    public void checkMaxWeight100IsOk() throws APIException {
        Solver solver = new Solver(
                "100 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        String result = solver.solve();
    }

    @Test(expected = APIException.class)
    public void checkPriceMoreThan100IsNotAccepted() throws APIException {
        Solver solver = new Solver(
                "99 : (1,53.38,€45) (2,88.62,€105) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        String result = solver.solve();
    }

    @Test(expected = APIException.class)
    public void checkWeightMoreThan100IsNotAccepted() throws APIException {
        Solver solver = new Solver(
                "99 : (1,102,€45) (2,88.62,€100) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        String result = solver.solve();
    }

    @Test
    public void checkWeighAndPrice100IsOk() throws APIException {
        Solver solver = new Solver(
                "99 : (1,100,€100) (2,88.62,€100) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)");
        String result = solver.solve();
    }

    @Test(expected = APIException.class)
    public void checkWrongTaskFormatIsNotAccepted() throws APIException {
        Solver solver = new Solver(
                "81:(1,53.38,€45)(2,88.62,€98)(3,78.48,€3)(4,72.30,€76)(5,30.18,€9)(6,46.34,€48)");
        String result = solver.solve();
    }

    @Test
    public void checkLighterThingsWithTheSamePriceIsChosen() {
        Solver solver = new Solver("56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16)" +
                " (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)");
        try {
            String result = solver.solve();
            assertEquals("wrong calculation", "8,9", result);
        } catch(APIException e){
            fail("error while process task data");
        }
    }

    @Test
    public void checkIntegerTaskValuesAreOk() {
        Solver solver = new Solver(
                "81 : (1,53,€45) (2,88,€98) (3,78,€3) (4,72,€76) (5,30,€9) (6,46,€48)");
        try {
            String result = solver.solve();
            assertEquals("wrong calculation", "4", result);
        } catch(APIException e){
            fail("error while process task data");
        }
    }

    @Test
    public void checkDecimalTaskValuesAreOk() {
        Solver solver = new Solver(
                "81 : (1,53.56,€45.8) (2,88.33,€98.44) (3,78,€3.5) " +
                        "(4,72.222,€76.4) (5,30.7,€9.55) (6,46.74,€48.64)");
        try {
            String result = solver.solve();
            assertEquals("wrong calculation", "4", result);
        } catch(APIException e){
            fail("error while process task data");
        }
    }

}