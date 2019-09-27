package tictactoe.controller;

import java.util.*;

/**
 * @author Kneotrino
 * @date 23/09/19
 */
public class PermutationFactory {

    int BOARD_SIZE = 0;
    Map<String,Integer> EmulatedMapField;
    int EmulatedPlayer = 0;
    int EmulatedTurns  = 0;
    public void  ChangePlayer(){
        EmulatedPlayer = EmulatedPlayer * -1;
    }

    private void setUpField() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                String key = i+"-"+j;
                EmulatedMapField.put(key, 0);
            }
        }
    }


    public int CalculateWin(List<String> moves,int emulatedPlayer,int Size){
        this.BOARD_SIZE = Size;
        EmulatedMapField.clear();
        EmulatedPlayer = emulatedPlayer;
        EmulatedTurns = 0;

        setUpField();

        int win = 0;

        for (String move: moves) {
            EmulatedMapField.put(move,EmulatedPlayer);
            win = checkWin(move);
            EmulatedTurns++;
            if (win != 0)
                break;
            ChangePlayer();
        }
//        System.out.println("EmulatedMapField = " + EmulatedMapField.values());
//        System.out.println("EmulatedTurns = " + EmulatedTurns+";" + "Player = " + EmulatedPlayer);
        return win;
    }

    private boolean checkTie() {
        return EmulatedTurns == BOARD_SIZE * BOARD_SIZE;
    }

    private int checkWin(String key) {
//        System.out.println("key = " + key);
        String[] split = key.split("-");
        int colomn = Integer.parseInt(split[0]);
        int row = Integer.parseInt(split[1]);
        if (!checkTie())
            return checkWinColumn(colomn) + checkWinRow(row) + checkWinDiagonalMajor() + checkWinDiagonalMinor();
        else
            return 0;
    }



    private int checkWinColumn(int colomn) {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = colomn+"-"+i;
            Integer field = EmulatedMapField.get(keyCheck);
            if (field == EmulatedPlayer) {
                WinningCondition++;
            }
        }
        if (WinningCondition == BOARD_SIZE) {
            return EmulatedPlayer;
        }
        return 0;
    }

    private int checkWinRow(int row) {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+row;
            Integer field = EmulatedMapField.get(keyCheck);
            if (field == EmulatedPlayer) {
                WinningCondition++;
            }
        }
        if (WinningCondition == BOARD_SIZE) {
            return EmulatedPlayer;
        }
        return 0;
    }

    private int checkWinDiagonalMajor() {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+i;
            Integer field = EmulatedMapField.get(keyCheck);
            if (field == EmulatedPlayer) {
                WinningCondition++;
            }
        }
        if (WinningCondition == BOARD_SIZE) {
            return EmulatedPlayer;
        }
        return 0;
    }

    private int checkWinDiagonalMinor() {
        int WinningCondition = 0;
        int k = BOARD_SIZE;
        for (int i=0; i < k; ++i) {
            String keyCheck = i+"-"+(k - 1 - i);
            Integer field = EmulatedMapField.get(keyCheck);
            if (field == EmulatedPlayer) {
                WinningCondition++;
            }
        }
        if (WinningCondition == BOARD_SIZE) {
            return EmulatedPlayer;
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