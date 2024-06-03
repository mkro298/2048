package org.cis1200.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


@SuppressWarnings("serial")
public class Board extends JPanel {

    private Twenty ttt; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 400;
    public static final int BOARD_HEIGHT = 400;

    private int score;

    /**
     * Initializes the game board.
     */
    public Board(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        ttt = new Twenty(); // initializes model for the game
        status = statusInit; // initializes the status JLabel
        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (ttt.gameOver()) {
                    return;
                }
                int key = e.getKeyCode();
                switch (key) {
                    case 38: {
                        ttt.playTurn(Twenty.DIR.UP);
                        repaint();
                        break;
                    }
                    case 40: {
                        ttt.playTurn(Twenty.DIR.DOWN);
                        repaint();
                        break;
                    }
                    case 39: {
                        ttt.playTurn(Twenty.DIR.RIGHT);
                        repaint();
                        break;
                    }
                    case 37: {
                        ttt.playTurn(Twenty.DIR.LEFT);
                        repaint();
                        break;
                    }
                }
                updateStatus(); // updates the status JLabel
                 // repaints the game board
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void play() {
        ttt.reset();
        score = ttt.readHighScore();
        status.setText("Your Score: " + ttt.getScore() + " High Score:" + score);
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void reset() {
        ttt.reset();
        status.setText("Your Score: 0 " + " High Score:" + ttt.getHighScore());
        repaint();

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    public void undo() {
        ttt.undo();
        repaint();

        requestFocusInWindow();
    }

    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        if (ttt.gameOver()) {
            status.setText("Game Over");
        }
        else {
            status.setText("Your Score: " + ttt.getScore() + " High Score:" + score);
        }
    }

 
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draws board grid
        g.drawLine(100, 0, 100, 400);
        g.drawLine(200, 0, 200, 400);
        g.drawLine(300,0,300,400);
        g.drawLine(0, 100, 400, 100);
        g.drawLine(0, 200, 400, 200);
        g.drawLine(0,300,400,300);

        // Draws X's and O's
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int state = ttt.getCell(j, i);
                if (state != 0) {
                    g.setColor(Color.BLACK);
                    if (ttt.checkNewKey(j, i)) {
                        g.setColor(Color.BLUE);
                    }
                    g.drawString(Integer.toString(state), 50 + 100 * j, 50 + 100 * i);
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
