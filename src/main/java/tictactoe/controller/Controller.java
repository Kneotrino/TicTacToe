/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe.controller;

import java.util.*;

import com.google.common.collect.Lists;
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
    private final mainView View;
    public static boolean RESTARTABLE= false;
    private final Map<String,Integer> mapField = new HashMap<>();
    private final Map<String,JButton> mapButton = new HashMap<>();
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
    public boolean botEnabled = false;
    public int level = 0;

    public Controller(mainView view,int Size, int level) {
        this.BOARD_SIZE = Size;
        View = view;
        if (level > 0) {
            this.botEnabled = true;
        }
        this.level = level;
        RESTARTABLE = true;

        System.out.println("BOARD_SIZE = " + BOARD_SIZE);
        System.out.println("level = " + level);
        System.out.println("botEnabled = " + botEnabled);
    }

    public Controller(mainView mainView, int Size, int level, boolean restartAble) {
        this.BOARD_SIZE = Size;
        View = mainView;
        if (level > 0) {
            this.botEnabled = true;
        }
        this.level = level;
        System.out.println("BOARD_SIZE = " + BOARD_SIZE);
        System.out.println("level = " + level);
        System.out.println("botEnabled = " + botEnabled);
        System.out.println("restartAble = " + restartAble);
    }

    public Map<String, Integer> getMapField() {
        return mapField;
    }

    public Map<String, JButton> getMapButton() {
        return mapButton;
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

    public void initGame() {
        currentState = PLAYING; 
        currentPlayer = CROSS;  
        TURNS = 0;
        setUpField();
        setUpButton();        
        showUpdate();
    }
    public void  ChangePlayer(){
//        System.out.println("OldPlayer = " + currentPlayer+"; NewPlayer = " + (currentPlayer * -1));
        currentPlayer = currentPlayer * -1;
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
    
    public void PlayerInput(JButton Button,String key) {
        Integer get = mapField.get(key);
        if (get == EMPTY) {
            Button.setText(currentPlayer==-1?"X":"O");            
            mapField.put(key, currentPlayer);
            TURNS++;
            checkWin(key);
            //Change Player
            currentPlayer = currentPlayer * -1;

            if ((botEnabled) && TURNS % 2 == 1)
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
        max--;
        if (min >= max) {
            return min;
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public String GenerateEasyBotMove( List<String> validBotMove){
        return validBotMove.get(getRandomNumberInRange(validBotMove.size()));
    }

    public List<String> getValidBotMove()
    {
        List<String> mutableList = Lists.newArrayList();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                String key = j+"-"+i;
                Integer value = mapField.get(key);
                if (value == EMPTY)
                    mutableList.add(key);
            }
        }
        return mutableList;
    }

    private void botInput() {
        String move = "";
        if (level == 1)
            move = GenerateEasyBotMove(getValidBotMove());
        else if (level == 2)
            move = GenerateMediumBotMove(getValidBotMove());
        System.out.println("move = " + move);
        JButton jButton = mapButton.get(move);
        PlayerInput(jButton,move);
    }

    public void botInputPlayer(String key){
        JButton jButton = mapButton.get(key);
        PlayerInput(jButton,key);
    }

    /*
    * Using MinMax Algorithme to move closer to win condition while distrubing opponnet movement
    *
    * */
    public String GenerateMediumBotMove(List<String> validBotMove) {
        return null;
    }


    private void setUpField() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {    
                String key = i+"-"+j;
                mapField.put(key, EMPTY);
            }
        }                
    }

    public int getCheckWin(String key){
        return checkWin(key);
    }

    private int checkWin(String key) {
        String[] split = key.split("-");
        int colomn = Integer.parseInt(split[0]);
        int row = Integer.parseInt(split[1]);
        if (!checkTie())
            return checkWinColumn(colomn) + checkWinRow(row) + checkWinDiagonalMajor() + checkWinDiagonalMinor();
        else
            return 0;
    }

    private void Tie() {
        boolean askRestartBox = false;
        if (RESTARTABLE)
            askRestartBox = mainView.askRestartBox("GAME END IN TIE\nDo you want to play again", "TIE");
        if (askRestartBox) {
            restartGame();
        } else {
            System.exit(TURNS);
        }
    }

    private void Winning(String info){
        System.out.println("info = " + currentPlayer + info);
        showWinBox(info);
    }

    private int checkWinColumn(int colomn) {
        Set<String> moves = new LinkedHashSet<>();

        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = colomn+"-"+i;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
                moves.add(keyCheck);
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            Winning(" HAS WON IN COLUMN " + (colomn + 1) );
            System.out.println("moves = " + moves);
            return currentPlayer;
        }
        return 0;
    }
    
    private int checkWinRow(int row) {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+row;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            Winning(" HAS WON IN ROW " + (row + 1) );
            return currentPlayer;
        }
        return 0;
    }

    private int checkWinDiagonalMajor() {
        int WinningCondition = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            String keyCheck = i+"-"+i;
            Integer field = mapField.get(keyCheck);
            if (field == currentPlayer) {
                WinningCondition++;
            }            
        }
        if (WinningCondition == BOARD_SIZE) {
            Winning(" HAS WON IN DIAGONAL MAJOR" );
            return currentPlayer;
        }
        return 0;
    }

    private int checkWinDiagonalMinor() {
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
            Winning(" HAS WON IN DIAGONAL MINOR" );
            return currentPlayer;
        }
        return 0;
    }

    private void showWinBox(String info)
    {
        String msg = currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER; 
        msg += info;
        msg += "\nDO YOU WANT TO PLAY AGAIN?";
        currentState = currentPlayer;
        //Ask for restart
        boolean askRestartBox = false;
        if (RESTARTABLE)
            askRestartBox = mainView.askRestartBox(msg, "WINNING");
        if (askRestartBox) {
            restartGame();
        } else {
            System.exit(TURNS);
        }
    }   

    private boolean checkTie() {
        if (TURNS == BOARD_SIZE*BOARD_SIZE) {
            Tie();
            return true;
        }
        return false;
    }

    private void restartGame() {
        TicTacToe.restart();        
    }

    private void showUpdate() {
//        mainView.infoBox(currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER, "PLAYING");
        View.ShowPlayerTurn(currentPlayer==-1?CROSS_PLAYER:NOUGHT_PLAYER);
        View.ShowTurnSize(TURNS+1);
    }
}
