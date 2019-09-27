package tictactoe.controller;

//import org.junit.jupiter.api.BeforeAll;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;
import tictactoe.Gui.mainView;

import javax.swing.*;

import java.sql.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kneotrino
 * @date 18/09/19
 */
class ControllerTest {

    static int Size = 4, level = 0;
    private static PermutationFactory instance;
    private static Controller controller;

    @org.junit.jupiter.api.BeforeAll
    static void Init() {
        controller = new Controller(new mainView(), Size, level, false);
        controller.initGame();

        instance = PermutationFactory.getInstance(controller.getValidBotMove().stream().toArray(String[]::new));
//        controller.showGame();
    }

    @Test
    void TestSizeValidMove(){
        assertEquals(Size*Size,controller.getValidBotMove().size());
        assertEquals(0,controller.TURNS);
    }

    void Sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void TestBotEasy() {

        while (controller.getValidBotMove().size() > 0)
        {
            String move = controller.GenerateEasyBotMove(controller.getValidBotMove());
            System.out.println("Controller.PlayerInput = "+ controller.currentPlayer +"; Move ="+ move+";" +
                    "\nTurns "+controller.TURNS+"; CheckWin = " + controller.getCheckWin(move));
            JButton jButton = controller.getMapButton().get(move);
            controller.PlayerInput(jButton,move);
            this.Sleep(200l);
            System.out.println();
            if (controller.getCheckWin(move) != 0) {
                assertNotEquals(0,controller.getCheckWin(move));
//                return;
            }
        }
    }

    @Test
    void TestBotMedium() {
        while (controller.getValidBotMove().size() > 0)
        {
            String move = controller.GenerateMediumBotMove(controller.getValidBotMove());
            System.out.println("Controller.PlayerInput = "+ controller.currentPlayer +"; Move ="+ move+";" +
                    "\nTurns "+controller.TURNS+"; CheckWin = " + controller.getCheckWin(move));
            JButton jButton = controller.getMapButton().get(move);
            controller.PlayerInput(jButton,move);
            this.Sleep(200l);
            System.out.println();
            if (controller.getCheckWin(move) != 0) {
                return;
            }
        }
    }

    @Test
    void Generatatepossibility(){
        List<String> validBotMove = controller.getValidBotMove();
        System.out.println("validBotMove = " + validBotMove);

        Set<List<String>> permutateList = instance.getPermutateList();

        Multimap<String, List<String>> listMultimap = ArrayListMultimap.create();

        for (List<String> moves : permutateList) {
            listMultimap.put(moves.get(0),moves);
        }
        Collection<List<String>> listCollection = listMultimap.get("1-1");
        System.out.println("collection.size() = " + listCollection.size());

        assertEquals(instance.factorialUsingForLoop(Size*Size),permutateList.size());
        assertEquals(instance.factorialUsingForLoop(Size*Size)/9,listCollection.size());
    }
    @Test
    void TestBestMove(){
        List<String> validBotMove = controller.getValidBotMove();
        System.out.println("validBotMove = " + validBotMove);

        Set<List<String>> permutateList = instance.getPermutateList();

        Multimap<String, List<String>> listMultimap = ArrayListMultimap.create();
        permutateList.forEach(moves -> listMultimap.put(moves.get(0), moves));

        Collection<List<String>> listCollection = listMultimap.get("1-1");

        long summingWin = 0l;
        long summingLose = 0l;
        long summingTie = 0l;

        for (List<String> moves : listCollection) {
            int calculateWin = instance.CalculateWin(moves, 1, controller.BOARD_SIZE);
            switch (calculateWin) {
                case 0 :summingTie++;
                break;
                case 1 :summingWin++;
                break;
                case -1:summingLose++;
                break;
            }

        }

        System.out.println("collection.size() = " + listCollection.size());
        System.out.println("summingWin = " + summingWin);
        System.out.println("summingLose = " + summingLose);
        System.out.println("summingTie = " + summingTie);

    }

    @Test
    void TestCalcutedWin() {
        List<String> validBotMove = controller.getValidBotMove();
//        System.out.println("validBotMove = " + validBotMove);
        validBotMove.clear();//Tie

        validBotMove.add("1-1"); // X
        validBotMove.add("0-0"); // 0

        validBotMove.add("2-2"); // X
        validBotMove.add("2-0"); // 0

        validBotMove.add("0-2"); // X
        validBotMove.add("1-2"); // 0

        validBotMove.add("0-1"); // X
        validBotMove.add("2-1"); // 0

        validBotMove.add("0-1"); // X

        int calculateWin = instance.CalculateWin(validBotMove, -1, controller.BOARD_SIZE);
        assertEquals(0,calculateWin);
    }
}