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
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner sc = new Scanner(new File(filePath), "UTF-8")) {
            while (sc.hasNextLine()) {
                Solver solver = new Solver(sc.nextLine());
                stringBuilder.append(solver.solve());
                if (sc.hasNextLine()) {
                    stringBuilder.append("\n");
                }
            }
            if (sc.ioException() != null) {
               throw new APIException();
            }
        } catch (FileNotFoundException e) {
            throw new APIException();
        }
       return stringBuilder.toString();
    }

}


