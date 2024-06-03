package org.cis1200.game;


import javax.swing.*;
import java.awt.*;


public class Run2048 implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(400, 400);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final Board board = new Board(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

       
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        final JButton undo = new JButton("Undo");
        undo.addActionListener(e -> board.undo());
        control_panel.add(undo);

       final JButton instr = new JButton("Instructions");
        instr.addActionListener(e ->
                JOptionPane.showMessageDialog(board,
                "Press the keys UP, DOWN, LEFT, RIGHT to merge the tiles to get a score " +
                        "of 2048 to win."));
        control_panel.add(instr);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.play();
    }
}