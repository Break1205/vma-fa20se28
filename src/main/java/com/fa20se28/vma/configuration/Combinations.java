package com.fa20se28.vma.configuration;


import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Combinations {
    private int[] repeats;
    private List<Integer> numbers;
    private Integer target;
    private Integer sum;
    private boolean hasNext;
    private int limit;
    private List<List<Integer>> combinationList;

    // Constructor
    public Combinations(List<Integer> numbers, Integer target, int limit) {
        this.numbers = numbers;
        this.numbers.removeAll(Arrays.asList(0));
        Collections.sort(this.numbers);

        this.limit = limit;
        this.target = target;
        this.repeats = new int[this.numbers.size()];
        this.combinationList = new LinkedList<>();

        this.sum = 0;
        this.hasNext = this.repeats.length > 0;
    }

    // Get combinations
    public void calculateCombinations() {
        while (this.hasNext) {
            if (this.next().compareTo(target) == 0) {
                if (this.getCombinationAsList().size() <= limit) {
                    combinationList.add(this.getCombinationAsList());
                }
            }
        }
    }

    // Calculate the sum of the next combination
    private Integer next() {
        if (this.hasNext && this.repeats.length > 0) {
            this.repeats[0] += 1;
            this.calculateSum();

            for (int i = 0; i < this.repeats.length && this.sum != 0; ++i) {
                if (this.sum > this.target) {
                    this.repeats[i] = 0;
                    if (i + 1 < this.repeats.length) {
                        this.repeats[i + 1] += 1;
                    }
                    this.calculateSum();
                }
            }

            if (this.sum.compareTo(0) == 0)
                this.hasNext = false;
        }
        return this.sum;
    }

    // Calculate sum of the current combination
    private Integer calculateSum() {
        this.sum = 0;
        for (int i = 0; i < repeats.length; ++i) {
            this.sum += repeats[i] * numbers.get(i);
        }

        return this.sum;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("" + sum + ": ");
        for (int i = 0; i < repeats.length; ++i) {
            for (int j = 0; j < repeats[i]; ++j) {
                stringBuilder.append(numbers.get(i)).append(" ");
            }
        }
        return stringBuilder.toString();
    }

    private List<Integer> getCombinationAsList() {
        List<Integer> list = new LinkedList<>();

        for (int i = 0; i < repeats.length; ++i) {
            for (int j = 0; j < repeats[i]; ++j) {
                list.add(numbers.get(i));
            }
        }

        return list;
    }

    public List<List<Integer>> getResult() {
        return this.combinationList;
    }
}
