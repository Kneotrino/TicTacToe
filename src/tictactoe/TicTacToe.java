/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import tictactoe.Gui.mainView;
import tictactoe.controller.Controller;

/**
 *
 * @author Rinoier
 */
public class TicTacToe {

    /**
     * Main class
     * Initialize  and start game
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.start();
    }


    /**
     * Initialize Controller and GUI
     */
    public  static Controller controller;
    private static final int BOARD_SIZE = 3; // Scalable 2>
    private static boolean BOT_ENABLED = true;
    public  static mainView view;
    
    /**
     * Main class
     * to boot the game
     */
    public TicTacToe() {
        int TicTacToeGrid = mainView.TicTacToeBox(BOARD_SIZE);        
        view = new mainView();
        controller = new Controller(view, TicTacToeGrid,BOT_ENABLED);
    }

    /**
     * What to expect? just start play
     */
    public void start() {
        controller.play();
    }
    
    /**
     * Well if you like it you can play again
     */
    public static void restart() {
        int TicTacToeGrid = mainView.TicTacToeBox(BOARD_SIZE);   
        view.dispose();
        view = new mainView();
        controller = new Controller(view, TicTacToeGrid,BOT_ENABLED);
        controller.play();
    }        
}
