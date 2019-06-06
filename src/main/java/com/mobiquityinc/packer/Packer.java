package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by Cadmy on 04.06.2019.
 */
public class Packer {

    public static String pack(String filePath) throws APIException {
        try (Scanner sc = new Scanner(new File(filePath), "UTF-8")) {
            while (sc.hasNextLine()) {
                Solver solver = new Solver(sc.nextLine());

                try {
                    solver.parseTask();
                } catch (NumberFormatException e) {
                    throw new APIException();
                }

                return solver.solve();
            }
            if (sc.ioException() != null) {
                System.out.println("scanner ex");
            }
        } catch (FileNotFoundException e) {
            throw new APIException();
        }
    }

}


