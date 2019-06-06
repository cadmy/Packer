package com.mobiquityinc.packer;

import com.mobiquityinc.packer.entity.Thing;
import com.mobiquityinc.packer.exception.APIException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cadmy on 06.06.2019.
 */
public class Solver {

    private String taskData;
    private BigDecimal maxWeight;
    private List<Thing> things;

    public Solver(String taskData) {
        this.taskData = taskData;
        this.things = new ArrayList<>();
    }

    private void parseTask() throws NumberFormatException, APIException {
        if (taskData != null && !taskData.isEmpty()) {
            List<String> dataItems = Arrays.asList(taskData.split(" "));

            if (dataItems.size()<2 || !dataItems.get(1).equals(":")) {
                throw new APIException();
            }

            maxWeight = new BigDecimal(dataItems.get(0));

            if (maxWeight.compareTo(BigDecimal.valueOf(100)) == 1) {
                throw new APIException();
            }

            if (dataItems.size() > 17) {
                throw new APIException();
            }

            for (int i = 2, dataItemsSize = dataItems.size(); i < dataItemsSize; i++) {
                String packItem = dataItems.get(i);
                if (!packItem.startsWith("(") || !packItem.substring(packItem.length() - 1).equals(")")) {
                    throw new APIException();
                }
                packItem = packItem.substring(1, packItem.length()-1);
                List<String> itemContent = Arrays.asList(packItem.split(","));
                int itemIndex = Integer.valueOf(itemContent.get(0));
                BigDecimal itemWeight = new BigDecimal(itemContent.get(1));
                BigDecimal itemCost = new BigDecimal(itemContent.get(2).replaceAll("[^0-9.,]", ""));

                if (itemWeight.compareTo(BigDecimal.valueOf(100)) == 1 ||
                        itemCost.compareTo(BigDecimal.valueOf(100)) == 1) {
                    throw new APIException();
                }

                things.add(new Thing(itemIndex, itemWeight, itemCost));
            }
        } else {
            throw new APIException();
        }
    }

    public String solve() throws APIException {
        try {
            parseTask();
        } catch (NumberFormatException e) {
            throw new APIException("Invalid task data. ", e);
        }
        List<Thing> bestThings = new ArrayList<>();
        if (fillPackage(maxWeight, things, bestThings, things.size()).signum() == 0) {
            return "-";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Thing> iterator = bestThings.iterator(); iterator.hasNext(); ) {
            Thing thing = iterator.next();
            stringBuilder.append(thing.getIndex());
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }

    private BigDecimal fillPackage(BigDecimal weight, List<Thing> things, List<Thing> bestThings, int counter) {
        if (counter == 0 || weight.signum() == 0) {
            return BigDecimal.valueOf(0);
        }
        if (things.get(counter - 1).getWeight().compareTo(weight) == 1) {
            List<Thing> subOptimalChoice = new ArrayList<>();
            BigDecimal optimalCost = fillPackage(weight, things, subOptimalChoice, counter - 1);
            bestThings.addAll(subOptimalChoice);
            return optimalCost;
        } else {
            List<Thing> includeOptimalChoice = new ArrayList<>();
            List<Thing> excludeOptimalChoice = new ArrayList<>();
            BigDecimal includeCost = things.get(counter - 1).getCost().add(
                    fillPackage((weight.subtract(things.get(counter - 1).getWeight())),
                            things, includeOptimalChoice, counter - 1));
            BigDecimal excludeCost = fillPackage(weight, things, excludeOptimalChoice, counter - 1);
            if (includeCost.compareTo(excludeCost) == 1) {
                bestThings.addAll(includeOptimalChoice);
                bestThings.add(things.get(counter - 1));
                return includeCost;
            } else if (includeCost.compareTo(excludeCost) == 0) {
                for (Thing sameCostThing : excludeOptimalChoice) {
                    if (sameCostThing.getCost().equals(includeCost)) {
                        if (sameCostThing.getWeight().compareTo(things.get(counter - 1).getWeight()) == 1) {
                            bestThings.addAll(includeOptimalChoice);
                            bestThings.add(things.get(counter - 1));
                            return includeCost;
                        }
                    }
                }
                bestThings.addAll(excludeOptimalChoice);
                return excludeCost;
            } else {
                bestThings.addAll(excludeOptimalChoice);
                return excludeCost;
            }
        }
    }
}
