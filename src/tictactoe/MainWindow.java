/*
Created by: Iraklis Kostalas
AM: 121013
Class: Friday 12:00-14:00
TEI of ATHENS 2016

Notes: I could have made it so there is a player class where the score and other relevant values are kept. I decided against it since there are only two players 
so it wasn't really needed.
 */
package tictactoe;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MainWindow extends JFrame implements ActionListener {

    //Buttons
    JButton[][] button = new JButton[3][3];
    JButton resetBtn = new JButton("Reset Game");
    JButton resetScoreBtn = new JButton("Reset Score");
    //END

    //This keeps the values of the button grid and helps with the checks necessary to determine win/draw conditions.
    //It gets three values: 'X', 'O' or '-'.
    char[][] btn = new char[3][3];
    //END

    //Panels
    JPanel gamePanel;
    JPanel resultPanel;
    JPanel scorePanel;
    //END

    //Possible image icons for the buttons
    ImageIcon emptyIcon = new ImageIcon("src/icons/empty-icon.png");
    ImageIcon xIcon = new ImageIcon("src/icons/X-icon.png");
    ImageIcon oIcon = new ImageIcon("src/icons/O-icon.png");
    //END

    //Player turn label.
    JLabel playerLbl = new JLabel("It's player 1's turn");
    //END

    //Score keeping stuff
    int p1score;
    int p2score;
    JLabel player1ScoreLbl = new JLabel("Player 1 |");
    JLabel player2ScoreLbl = new JLabel(" Player 2");
    JLabel player1Score = new JLabel(String.valueOf(p1score));
    JLabel player2Score = new JLabel(String.valueOf(p2score));
    //END

    //This is here to alternate between which player's turn it is.
    boolean playerTurn = false; //default is false, which means it's player 1's turn
    //END

    //This checks if the game is over.
    boolean gameOver = false;
    //END

    //Class constructor
    public MainWindow() {

        setTitle(
                "Tic Tac Toe");
        setSize(
                500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(
                null);
        setLayout(
                new FlowLayout());

        //Create panels and sub-panels
        gamePanel = new JPanel(new GridLayout(3, 3));
        resultPanel = new JPanel(new GridLayout(3, 1));
        scorePanel = new JPanel(new GridLayout(2, 2));
        //END

        resultPanel.add(playerLbl);

        resultPanel.add(resetBtn);

        scorePanel.add(player1ScoreLbl);

        scorePanel.add(player2ScoreLbl);

        scorePanel.add(player1Score);

        scorePanel.add(player2Score);

        scorePanel.setBorder(
                new TitledBorder("Score"));
        playerLbl.setBorder(
                new TitledBorder("Turn"));

        //Creates the buttons in a 3x3 grid, gives them an empty icon image 
        //and add the action listener to each one.
        for (int i = 0;
                i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btn[i][j] = '-';
                button[i][j] = new JButton(emptyIcon);
                button[i][j].setIcon(emptyIcon);
                button[i][j].addActionListener(this);
                gamePanel.add(button[i][j]);
            }
        }
        //END

        //Add the panels
        add(gamePanel);

        add(resultPanel);

        resultPanel.add(scorePanel);

        resultPanel.add(resetScoreBtn);
        //END

        //Add the rest of the action listeners
        resetBtn.addActionListener(
                this);
        resetScoreBtn.addActionListener(
                this);
        //END

        pack();

    }

    /**
     * Player 1 Turn
     *
     * @param i the i index of the button array
     * @param j the j index of the button array
     */
    public void player1Turn(int i, int j) {
        button[i][j].setIcon(xIcon);
        button[i][j].setEnabled(false); //Disables clicked buttons
        btn[i][j] = 'X';
        playerLbl.setText("It's player 2's turn");
        playerTurn = !playerTurn; //makes it false
        if (checkWin("Player 1", btn[i][j])) {
            p1score = p1score + 1;
            player1Score.setText(String.valueOf(p1score));
        }
        checkDraw();

    }

    /**
     * Player 2 Turn
     *
     * @param i the i index of the button array
     * @param j the j index of the button array
     */
    public void player2Turn(int i, int j) {
        button[i][j].setIcon(oIcon);
        button[i][j].setEnabled(false); //Disables clicked buttons
        btn[i][j] = 'O';
        playerLbl.setText("It's player 1's turn");
        playerTurn = !playerTurn; //makes it true
        if (checkWin("Player 2", btn[i][j])) {
            p2score = p2score + 1;
            player2Score.setText(String.valueOf(p2score));
        }
        checkDraw();
    }

    /**
     * Checks if win conditions have been met for any player. If they have been met, it announces the winner and ends the game.
     *
     * @param player The player we are checking for.
     * @param playerSym The player's symbol (X or O) we are checking for.
     */
    public boolean checkWin(String player, char playerSym) {
        //Check collumns for win
        for (int j = 0; j < 3; j++) {
//            System.out.println(String.valueOf(btn[0][j]) + btn[1][j] + btn[2][j]);
            if ((btn[0][j] == playerSym) && (btn[1][j] == playerSym) && (btn[2][j] == playerSym)) {
                gameOver = true;
                playerLbl.setText(player + " wins!!!");
                System.out.println(player + " wins!!!");
                return true;
            }
            if ((btn[j][0] == playerSym) && (btn[j][1] == playerSym) && (btn[j][2] == playerSym)) {
                gameOver = true;
                playerLbl.setText(player + " wins!!!");
                System.out.println(player + " wins!!!");
                return true;
            }
        }
        if (((btn[0][0] == playerSym) && (btn[1][1] == playerSym) && (btn[2][2] == playerSym)) || (btn[0][2] == playerSym) && (btn[1][1] == playerSym) && (btn[2][0] == playerSym)) {
            gameOver = true;
            playerLbl.setText(player + " wins!!!");
            System.out.println(player + " wins!!!");
            return true;
        }

        return false;
    }

    /**
     * Checks if the grid is full. If it is and a win condition was not met, it means it's a draw. The method also takes care of changing the necessary labels to signify the draw and also end the game.
     */
    public boolean checkDraw() {
        if (!(checkWin("Player 1", 'X') && checkWin("Player 2", 'O'))) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (btn[i][j] == '-') {
                        return false;
                    }
                }
            }
        }
        gameOver = true;
        playerLbl.setText("It's a draw!");
        System.out.println("It's a draw!");
        return true;
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        gameOver = false;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                button[i][j].setIcon(emptyIcon);
                button[i][j].setEnabled(true); //Re-enables clicked buttons
                btn[i][j] = '-';
            }
        }
        playerTurn = false; //resets turn to player 1
        playerLbl.setText("It's player 1's turn");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(resetBtn)) {
            resetGame();
        } else if (e.getSource().equals(resetScoreBtn)) {
            p1score = 0;
            p2score = 0;
            player2Score.setText(String.valueOf(p2score));
            player1Score.setText(String.valueOf(p1score));
        } else if (!gameOver) { //Checks if the game is over. If it is, the buttons won't work until the game is reset.
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (e.getSource().equals(button[i][j])) {
                        if (playerTurn) {
                            //player 2 turn
                            player2Turn(i, j);
                        } else {
                            //player 1 turn
                            player1Turn(i, j);
                        }

                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        //Creates the window.
        new MainWindow().setVisible(true);
    }

}
