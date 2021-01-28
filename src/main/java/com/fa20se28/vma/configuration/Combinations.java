package com.fa20se28.vma.configuration;

import java.util.*;

public class Combinations {
    private List<Integer> availableSeats;
    private Set<List<Integer>> combinationSet;
    private Map<Integer, List<Integer>> combinationMap;
    private int vehicleCount;
    private int passengerCount;

    // Constructor
    public Combinations(List<Integer> availableSeats, int vehicleCount, int passengerCount) {
        this.availableSeats = availableSeats;
        this.availableSeats.removeAll(Collections.singletonList(0));
        Collections.sort(this.availableSeats);

        this.vehicleCount = vehicleCount;
        this.passengerCount = passengerCount;

        this.combinationSet = new HashSet<>();
        this.combinationMap = new HashMap<>();
    }

    public void calculateCombinations() {
        List<Integer> list = new ArrayList<>();
        getAllCombinations(this.availableSeats, this.vehicleCount, list, 0, this.passengerCount);
    }

    private void getAllCombinations(List<Integer> availableSeats, int vehicleCount, List<Integer> list, int start, int passengerCount) {
        if (list.size() >= vehicleCount) {
            int sum = 0;
            for (Integer number: list) {
                sum += number;
            }
            if (sum >= passengerCount) {
                List<Integer> temp = new ArrayList<>(list);
                this.combinationSet.add(temp);
            }
            return;
        }

        for (int i = start; i < availableSeats.size(); i++) {
            list.add(availableSeats.get(i));
            getAllCombinations(availableSeats, vehicleCount, list, i + 1, passengerCount);
            list.remove(list.size() - 1);
        }
    }


    public Map<Integer, List<Integer>> getResultMap() {
        for (List<Integer> combination: this.combinationSet) {
            int sum = 0;
            for (Integer number: combination) {
                sum += number;
            }
            this.combinationMap.put(sum, combination);
        }

        return new TreeMap<>(combinationMap);
    }
}
