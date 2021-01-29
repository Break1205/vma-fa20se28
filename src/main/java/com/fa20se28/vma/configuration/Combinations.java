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
        Integer[] temp = new Integer[availableSeats.size()];
        Integer[] data = new Integer[vehicleCount];
        getAllCombinations(availableSeats.toArray(temp), this.vehicleCount, data, 0, 0, this.passengerCount);
    }

    private void getAllCombinations(Integer[] availableSeats, int vehicleCount, Integer[] list, int index, int start, int passengerCount) {
        if (index == vehicleCount) {
            int sum = 0;
            for (Integer number : list) {
                sum += number;
            }
            if (sum >= passengerCount) {
                this.combinationSet.add(Arrays.asList(list));
            }
            return;
        }

        if (start >= availableSeats.length) {
            return;
        }

        list[index] = availableSeats[start];
        getAllCombinations(availableSeats, vehicleCount, list, index + 1, start + 1, passengerCount);

        getAllCombinations(availableSeats, vehicleCount, list, index, start + 1, passengerCount);
    }


    public Map<Integer, List<Integer>> getResultMap() {
        for (List<Integer> combination : this.combinationSet) {
            int sum = 0;
            for (Integer number : combination) {
                sum += number;
            }
            this.combinationMap.put(sum, combination);
        }

        return new TreeMap<>(combinationMap);
    }
}
