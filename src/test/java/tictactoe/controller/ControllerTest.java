package tictactoe.controller;

//import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tictactoe.Gui.mainView;

import javax.swing.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Kneotrino
 * @date 18/09/19
 */
class ControllerTest {

    private static Controller controller = new Controller(new mainView(),4,0);
    @org.junit.jupiter.api.BeforeAll
    static void Init() {

//        controller.initGame();
        controller.play();
    }

    @Test
    void TestSizeValidMove(){
        assertEquals(16,controller.getValidBotMove().size());
        assertEquals(0,controller.TURNS);
    }

//    @Test
    void TestCheckWinCross(){
        controller.getMapField().put("0-0",-1);
        controller.getMapField().put("0-1",-1);
        controller.getMapField().put("0-2",-1);
        controller.getMapField().put("0-3",-1);
        assertEquals(-1,controller.getCheckWin("0-3"));
        System.out.println("controller.getMapField().get(\"0-0\") = " + controller.getMapField().get("0-0"));
        System.out.println("controller.getMapField() = " + controller.getMapField());
    }
//    @Test
    void TestCheckWinNought() {
        controller.ChangePlayer();
        controller.getMapField().put("1-0",1);
        controller.getMapField().put("1-1",1);
        controller.getMapField().put("1-2",1);
        controller.getMapField().put("1-3",1);
        assertEquals(1,controller.getCheckWin("1-3"));
    }

    @Test
    void TestBotEasy() {
        for (int i = 0; i < 15; i++) {
            String move = controller.GenerateEasyBotMove(controller.getValidBotMove());
            System.out.println("Controller.PlayerInput = "+ controller.currentPlayer +"; Move ="+ move+"; Turns "+controller.TURNS);
            JButton jButton = controller.getMapButton().get(move);
            try {
                Thread.sleep(1000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            controller.PlayerInput(jButton,move);
            if (controller.getCheckWin(move) != 0) {
                return;
            }
        }
    }

    @Test
    void GeneratateTreeTest(){
//        String move = controller.GenerateMediumBotMove(controller.getValidBotMove());
//        System.out.println("move = " + move);
        List<String> botMove = controller.getValidBotMove();
        int sum = 0;
        for (String m : botMove) {
            controller.TURNS++;
            System.out.println("Controller.PlayerInput = "+ controller.currentPlayer +"; Move ="+ m+"; Turns "+controller.TURNS);
            JButton jButton = controller.getMapButton().get(m);
            controller.PlayerInput(jButton,m);
            controller.ChangePlayer();
            if (controller.getCheckWin(m) != 0)
            {
                try {
                    Thread.sleep(5000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("sum = " + sum);
    }
}