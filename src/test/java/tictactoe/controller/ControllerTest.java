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

    static int Size = 3, level = 0;
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
//        instance.showpermutateList();

        assertEquals(instance.factorialUsingForLoop(Size*Size),permutateList.size());
    }

    @Test
    void testCalcutedWin() {
        List<String> validBotMove = controller.getValidBotMove();
        System.out.println("validBotMove = " + validBotMove);
        int calculateWin = instance.CalculateWin(validBotMove, -1, true);
        assertEquals(-1,calculateWin);
    }
}