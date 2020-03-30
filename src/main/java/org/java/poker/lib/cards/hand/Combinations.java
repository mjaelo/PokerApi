
package org.java.poker.lib.cards.hand;

import java.util.ArrayList;

public abstract class Combinations {

    protected static ArrayList<int[]> combinations;

    static {
        combinations = generate(7,5);
    }

    private static void helper(ArrayList<int[]> combinations, int data[], int start, int end, int index) {
        if(index == data.length) {
            int[] combination = data.clone();
            combinations.add(combination);
        } else if(start <= end) {
            data[index] = start;
            helper(combinations, data, start + 1, end, index + 1);
            helper(combinations, data, start + 1, end, index);
        }
    }

    private static ArrayList<int[]> generate(int n, int r) {
        ArrayList<int[]> combinations = new ArrayList<>();
        helper(combinations, new int[r], 0, n-1, 0);
        return combinations;
    }
}
