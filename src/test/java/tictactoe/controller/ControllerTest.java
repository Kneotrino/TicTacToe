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

    static int Size = 2, level = 0;
    private static Controller controller;

    static {
    }

    @org.junit.jupiter.api.BeforeAll
    static void Init() {
        controller = new Controller(new mainView(), Size, level, false);
        controller.initGame();
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
                return;
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
        Multimap<String, String> listMultimap = ArrayListMultimap.create();
        List<String> validBotMove = controller.getValidBotMove();
        System.out.println("validBotMove = " + validBotMove);

        PermutationFactory instance = PermutationFactory.getInstance(validBotMove.stream().toArray(String[]::new));
        Set<List<String>> permutateList = instance.getPermutateList();
        instance.showpermutateList();

        assertEquals(instance.factorialUsingForLoop(Size*Size),permutateList.size());
    }
}