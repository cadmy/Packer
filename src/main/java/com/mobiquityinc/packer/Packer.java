package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The Packer class is responsible for reading a file with a task and show results provided by Solver.class.
 *
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
               throw new APIException("Error while processing file. ", sc.ioException());
            }
        } catch (FileNotFoundException e) {
            throw new APIException("Cannot find file. ", e);
        }
       return stringBuilder.toString();
    }

}


