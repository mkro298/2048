package org.cis1200.game;

public class GameStates {

    private int[][] internalGameState;

    public GameStates(int[][] gameState) {
        internalGameState = gameState;
    }

    public int[][] getInternalGameState() {
        return internalGameState;
    }
}
