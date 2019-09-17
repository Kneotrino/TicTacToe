/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import tictactoe.Gui.mainView;
import javax.swing.JButton;
import tictactoe.TicTacToe;

/**
 *
 * @author Rinoier
 */
public class Controller {
    // Name-constants to represent the seeds and cell contents

    /**
     * Constat to input in field
     */
    public static final int EMPTY = 0;
    public static final int CROSS = -1;
    public static final int NOUGHT = 1;
 
    /**
     * Name-constants to represent the various states of the game
     */
    public static final String CROSS_PLAYER = "[X] CROSS PLAYER";
    public static final String NOUGHT_PLAYER = "[O] NOUGHT PLAYER";
     

    /**
     * state of the game
     */
    public static final int PLAYING = 0;
    public static final int CROSS_WON = -1;
    public static final int NOUGHT_WON = 1;
    
    /**
     * BOARD_SIZE
     */
    public int BOARD_SIZE;

    /**
     * TURNS AROUND
     */
    public int TURNS;

    /**
     * the current state of the game
     * (PLAYING, DRAW, CROSS_WON, NOUGHT_WON)
     */
    public int currentState;  
    /**
     * the current player (CROSS or NOUGHT)
     */
    public int currentPlayer; 
    

    private final mainView View;
    private final Map<String,Integer> mapField = new HashMap<>();
    private final Map<String,JButton> mapButton = new HashMap<>();

    public boolean botEnabled = false;

    /**
     *
     * @param view
     * @param Size
     */
    public Controller(mainView view, int Size) {
        View = view;        
        BOARD_SIZE = Size;
    }

    public Controller(mainView view,int Size, boolean botEnabled) {
        this.BOARD_SIZE = Size;
        View = view;
        this.botEnabled = botEnabled;
    }

    private void showGame()
    {
        View.setVisible(true);    
    }

    /**
     *
     */
    public void play() {
        this.showGame();
        this.initGame();
    }

    private void initGame() {
        currentState = PLAYING; 
        currentPlayer = CROSS;  
        TURNS = 0;
        setUpField();
        setUpButton();        
        showUpdate();
    }
    
    private void setUpButton() {   
        View.PanelBoard.setLayout(new java.awt.GridLayout(BOARD_SIZE, BOARD_SIZE));            
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {    
                String key = j+"-"+i;
                javax.swing.JButton btn = new JButton();
                btn.addActionListener((java.awt.event.ActionEvent evt) -> {
                    PlayerInput(btn,key);
                });
                mapButton.put(key, btn);
                View.PanelBoard.add(btn);        
            }
        }
    }
    
    private void PlayerInput(JButton Button,String key) {
        Integer get = mapField.get(key);
        if (get == EMPTY) {
            Button.setText(currentPlayer==-1?"X":"O");            
            mapField.put(key, currentPlayer);
            TURNS++;
            checkWin(key);
            //Change Player
            currentPlayer = currentPlayer * -1;

            if (botEnabled)
                botInput();
            showUpdate();
        }
        else
        {
            mainView.invalidBox(currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER, "ERROR INPUT");            
        }
        
    }

    private int getRandomNumberInRange(int max) {
        int min = 0;
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    private void botInput() {
        System.out.println("Bot Move = "+ getRandomNumberInRange(BOARD_SIZE) +"-"+ getRandomNumberInRange(BOARD_SIZE) );
    }


    private void setUpField() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {    
                String key = i+"-"+j;
                mapField.put(key, EMPTY);
            }
        }                
    }

    private void checkWin(String key) {        
        String[] split = key.split("-");
        int colomn = Integer.parseInt(split[0]);
        int row = Integer.parseInt(split[1]);        
        checkWinColumn(colomn);
        checkWinRow(row);
        checkWinDiagonalMajor();
        checkWinDiagonalMinor();
        checkTie();
    }

    private void checkWinColumn(int colomn) {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = colomn+"-"+i;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            showWinBox(" HAS WON IN COLUMN " + (colomn + 1) ); 
        }
    }
    
    private void checkWinRow(int row) {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+row;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            showWinBox(" HAS WON IN ROW " + (row + 1) );            
        }
        
    }

    private void checkWinDiagonalMajor() {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+i;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            showWinBox(" HAS WON IN DIAGONAL MAJOR" );
        }                
    }

    private void checkWinDiagonalMinor() {
        int WinningCondition = 0;
        int k = BOARD_SIZE;
        for (int i=0; i < k; ++i) {
            String keyCheck = i+"-"+(k - 1 - i);            
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            showWinBox(" HAS WON IN DIAGONAL MINOR" );
        }                        
    }

    private void showWinBox(String info)
    {
        String msg = currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER; 
        msg += info;
        msg += "\nDO YOU WANT TO PLAY AGAIN?";
        currentState = currentPlayer;
        //Ask for restart
        boolean askRestartBox = mainView.askRestartBox(msg, "WINNING");
        if (askRestartBox) {
            restartGame();
        } else
        {
            System.exit(currentState);
        }
        
        
    }   

    private void checkTie() {
        if (TURNS == BOARD_SIZE*BOARD_SIZE) {
            //Ask for restart
            boolean askRestartBox = mainView.askRestartBox(
                    "GAME END IN TIE\nDo you want to play again"
                    , "WINNING");
            if (askRestartBox) {
                restartGame();
            } else
            {
                System.exit(currentState);
            }
        }                                
    }

    private void restartGame() {
        TicTacToe.restart();        
    }

    private void showUpdate() {
//        mainView.infoBox(currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER, "PLAYING");
        View.ShowPlayerTurn(currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER);
        View.ShowTurnSize(TURNS);
    }
}
