package tictactoe.controller;

import java.util.*;

/**
 * @author Kneotrino
 * @date 23/09/19
 */
public class PermutationFactory {

    public int CalculateWin(List<String> moves,int emulatedPlayer,boolean first){
        Map<String,Integer> emulatedMapField = new HashMap<>();
        for (String move: moves) {
            emulatedMapField.put(move,emulatedPlayer);
        }
        return 0;
    }


    public Set<List<String>> getPermutateList() {
        return permutateList;
    }

    private Set<List<String>> permutateList = new HashSet<>();

    public PermutationFactory() {
    }

    public long factorialUsingForLoop(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        System.out.println("factorial = " + fact);
        return fact;
    }

    public static PermutationFactory getInstance(String[] element){
        PermutationFactory permutationFactory = new PermutationFactory();
        permutationFactory.permute(element);
        return permutationFactory;
    }

    public static void main(String[] args) {
        PermutationFactory permutationFactory = new PermutationFactory();
        String[] nums = { "1", "2", "3","4" };
        permutationFactory.permute(nums);
        System.out.println(permutationFactory.permutateList);
    }

    public void showpermutateList(){
        System.out.println("permutateList = " + permutateList);
    }

    public void permute(String[] nums) {
        permutation(0, nums.length - 1, nums);
    }

    public void permutation(int start, int end, String[] nums) {
        if (start == end) {
            permutateList.add(new ArrayList<String>(Arrays.asList(nums)));
        }
        for (int i = start; i <= end; i++) {
            permutateList.add(swap(nums, start, i));
            permutation(start + 1, end, nums);
            permutateList.add(swap(nums, start, i));
        }
    }

    private List<String> swap(String[] arr, int a, int b) {
        if (a == b) {
            return new ArrayList<>(Arrays.asList(arr));
        }
        String  temp = arr[b];
        arr[b] = arr[a];
        arr[a] = temp;
        return new ArrayList<>(Arrays.asList(arr));
    }
}