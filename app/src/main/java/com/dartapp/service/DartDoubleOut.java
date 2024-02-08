package com.dartapp.service;

import android.annotation.SuppressLint;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DartDoubleOut extends AppCompatActivity {

    @SuppressLint("DefaultLocale")
    public List<Integer> getCheckoutCombinations() {
        StringBuilder combinations = new StringBuilder();
        List<Integer> possibleThrows = new ArrayList<>(Arrays.asList(50, 25));
        List<Integer> triples = getListOfTriples();
        List<Integer> doubles = getListOfDoubles();
        List<Integer> singles = getListOfSingles();


        for (int singleScore = 20; singleScore >= 1; singleScore--) {
            singles.add(singleScore);
        }

        possibleThrows.addAll(triples);
        possibleThrows.addAll(doubles);
        possibleThrows.addAll(singles);

        return possibleThrows;

    }

    public void findCombinations(int targetScore) {
        List<Integer> possibleScores = getCheckoutCombinations();
        List<Integer> possibleDouble3 = getListOfDoubles();
        possibleDouble3.add(50);
        Set<List<Integer>> listPossibleScores = new HashSet<>();

        for (int dart1 : possibleScores) {
            for (int dart2 : possibleScores) {
                for (int dart3 : possibleDouble3) {
                    if (dart1 + dart2 + dart3 == targetScore) {
                        List<Integer> outCombinations = Arrays.asList(dart1, dart2, dart3);
                        listPossibleScores.add(outCombinations);
                    }
                }
            }
        }
        System.out.println("Size: " + listPossibleScores.size());
        for (List<Integer> list : listPossibleScores) {
            if (!list.isEmpty()) {
                System.out.println("PossibleOut: " + list);
            }if (list.isEmpty()){
                System.out.println("No combination found for: " + targetScore);
            }
        }
    }

    private List<Integer> getListOfTriples() {
        List<Integer> triples = new ArrayList<>();
        for (int triple = 20; triple >= 1; triple--) {
            triples.add(triple * 3);
        }
        return triples;
    }

    private List<Integer> getListOfDoubles() {
        List<Integer> doubles = new ArrayList<>();
        for (int doubleScore = 20; doubleScore >= 1; doubleScore--) {
            doubles.add(doubleScore * 2);
        }
        return doubles;
    }

    private List<Integer> getListOfSingles() {
        List<Integer> singles = new ArrayList<>();
        for (int singleScore = 20; singleScore >= 1; singleScore--) {
            singles.add(singleScore);
        }
        return singles;
    }
}
