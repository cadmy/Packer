package com.mobiquityinc.packer;

import com.mobiquityinc.packer.exception.APIException;

/**
 * Created by Cadmy on 06.06.2019.
 */
public class Test {
    public static void main(String[] args) {
        try {
            Packer.pack("D:\\Workspace\\packages.txt");
        } catch (APIException e) {
            e.printStackTrace();
        }
    }
}
