package tictactoe.controller;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tictactoe.Gui.mainView;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kneotrino
 * @date 18/09/19
 */
class ControllerTest {

    private static Controller controller = new Controller(new mainView(),4,0);
    @org.junit.jupiter.api.BeforeAll
    static void Init() {
        controller.initGame();
    }

    @Test
    void TestSizeValidMove(){
        assertEquals(16,controller.getValidBotMove().size());
        assertEquals(0,controller.TURNS);
    }

    @Test
    void TestCheckWin(){
        controller.getMapField().put("0-0",-1);
        controller.getMapField().put("0-1",-1);
        controller.getMapField().put("0-2",-1);
        controller.getMapField().put("0-3",-1);
        assertEquals(-1,controller.getCheckWin("0-3"));
    }

    @Test
    void GeneratateTreeTest(){
        String move = controller.GenerateMediumBotMove(controller.getValidBotMove());
        System.out.println("move = " + move);
    }



}