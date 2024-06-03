package org.cis1200.game;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;


public class Twenty {

    private static final String FILE_NAME = "hw09_local_temp/files/PreviousState.txt";

    private int[][] board;
    private boolean[][] newKey;
    private LinkedList<GameStates> previousStates = new LinkedList<>();

   private ArrayList<Integer> prevOpenSpots = new ArrayList<>();
    private int openSpots;
    private boolean over;
    private int score;
    private int highScore;

    enum DIR {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Constructor sets up game state.
     */
    public Twenty() {
        reset();
    }
    public int getScore() {
        return score;
    }
    public void writeHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(FILE_NAME), false))) {
            writer.write("");
            if (score > highScore) {
                writer.append(Integer.toString(score));
            }
            else {
                writer.append(Integer.toString(highScore));
            }
            writer.newLine();
            writer.append(Integer.toString(score));
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    writer.newLine();
                    writer.append(Integer.toString(board[i][j]));
                }
            }
        } catch (IOException e) {
            System.out.println("File error");
        }
    }

    public int getHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_NAME)))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                return Integer.parseInt(line);
            }
        } catch (IOException e) {

        }
        return 0;
    }
    public int readHighScore() {

        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_NAME)))) {
            String line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                highScore = Integer.parseInt(line);
            }
            String li = reader.readLine();
            score = Integer.parseInt(li);
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    board[i][j] = Integer.parseInt(reader.readLine());
                }
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScore;
    }
    public void undo() {
        if (previousStates.size() <= 1 || prevOpenSpots.size() <= 1) {
            return;
        }
        board = previousStates.get(previousStates.size() - 2).getInternalGameState();
        printGameState();
        previousStates.remove(previousStates.size() - 1);
        newKey = new boolean[4][4];
        over = false;
        openSpots = prevOpenSpots.get(prevOpenSpots.size() - 2);
        prevOpenSpots.remove(prevOpenSpots.size() - 1);
    }
    public boolean addNewKey() {
        newKey = new boolean[4][4];
        if (openSpots <= 0) {
            return false;
        }
        int r = (int) (Math.random() * 4);
        int c = (int) (Math.random() * 4);


        for (int i = r; i < board.length; i++) {
            for (int j = c; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2;
                    newKey[i][j] = true;
                    openSpots--;
                    return true;
                }
            }
        }

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = 2;
                    newKey[i][j] = true;
                    openSpots--;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIfPosMove() {
        if (openSpots > 0) {
            return true;
        }
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = 0; j < board[0].length - 1; j++) {
                if (board[i][j] == board[i][j + 1]) {
                    return true;
                }
                if (board[i][j] == board[i + 1][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    
    public boolean playTurn(DIR d) {
        //based on direction we want to move the tiles,
        //move all tiles farthest to the right
        //then merge
        switch (d) {
            case UP: {
                for (int i = 0; i < board[0].length; i++) {
                    for (int j = 0; j < board.length; j++) {
                        if (board[j][i] != 0) {
                            slideToEdge(DIR.UP, j, i);
                        }
                    }
                }
                merge(d);
                addNewKey();
                break;
            }

            case DOWN: {
                for (int i = 0; i < board[0].length; i++) {
                    for (int j = board.length - 1; j >= 0; j--) {
                        if (board[j][i] != 0) {
                            slideToEdge(DIR.DOWN, j, i);
                        }
                    }
                }
                merge(d);
                addNewKey();
                break;
            }

            case LEFT: {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length; j++) {
                        if (board[i][j] != 0) {
                            slideToEdge(DIR.LEFT, i, j);
                        }
                    }
                }
                merge(d);
                addNewKey();
                break;
            }
            case RIGHT: {
                for (int i = 0; i < board.length; i++) {
                    for (int j = board[0].length - 1; j >= 0; j--) {
                        if (board[i][j] != 0) {
                            slideToEdge(DIR.RIGHT, i, j);
                        }
                    }
                }
                merge(d);
                addNewKey();
                break;
            }
        }
        if (!checkIfPosMove()) {
            over = true;
        }
        previousStates.add(new GameStates(copiedArray()));
        prevOpenSpots.add(openSpots);
        writeHighScore();
        return true;
    }

    public int[][] copiedArray() {
        int [][] myB = new int[board.length][];
        for (int i = 0; i < board.length; i++) {
            myB[i] = board[i].clone();
        }
        return myB;
    }

    public void slideToEdge(DIR dir, int r, int c) {
        switch (dir) {
            case UP: {
                for (int i = r - 1; i >=0; i--) {
                    if (board[i][c] == 0) {
                        board[i][c] = board[r][c];
                        board[r][c] = 0;
                        r = i;
                    }
                }
                break;
            }
            case DOWN: {
                for (int i = r + 1; i < board.length; i++) {
                    if (board[i][c] == 0) {
                        board[i][c] = board[r][c];
                        board[r][c] = 0;
                        r = i;
                    }
                }
                break;
            }
            case LEFT: {
                for (int i = c - 1; i >= 0; i--) {
                    if (board[r][i] == 0) {
                        board[r][i] = board[r][c];
                        board[r][c] = 0;
                        c = i;
                    }
                }
                break;
            }
            case RIGHT: {
                for (int i = c + 1; i < board[0].length; i++) {
                    if (board[r][i] == 0) {
                        board[r][i] = board[r][c];
                        board[r][c] = 0;
                        c = i;
                    }
                }
                break;
            }
        }
    }


    public void merge(DIR d) {
        switch (d) {
            case UP: {
               for (int i = 0; i < board[0].length; i++) {
                   for (int j = 0; j < board.length - 1; j++) {
                       if (board[j][i] == board[j + 1][i]) {
                           board[j][i] = board[j][i] * 2;
                           board[j + 1][i] = 0;
                           openSpots++;
                           score += (board[i][j] * 2);
                       }
                       slideToEdge(DIR.UP, j, i);
                   }
               }
               break;
            }
            case DOWN: {
                for (int i = 0; i < board[0].length; i++) {
                    for (int j = board.length - 1; j > 0; j--) {
                        if (board[j][i] == board[j - 1][i]) {
                            board[j][i] = board[j][i] * 2;
                            board[j - 1][i] = 0;
                            openSpots++;
                            score += (board[i][j] * 2);
                        }
                        slideToEdge(DIR.DOWN, j, i);
                    }
                }
                break;
            }
            case LEFT: {
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length - 1; j++) {
                        if (board[i][j] == board[i][j + 1]) {
                            board[i][j] = board[i][j] * 2;
                            board[i][j + 1] = 0;
                            openSpots++;
                            score += (board[i][j] * 2);
                        }
                        slideToEdge(DIR.LEFT, i, j);
                    }
                }
                break;
            }
            case RIGHT: {
                for (int i = 0; i < board.length; i++) {
                    for (int j = board[0].length - 1; j > 0; j--) {
                        if (board[i][j] == board[i][j - 1]) {
                            board[i][j] = board[i][j] * 2;
                            board[i][j - 1] = 0;
                            openSpots++;
                            score += (board[i][j] * 2);
                        }
                        slideToEdge(DIR.RIGHT, i, j);
                    }
                }
                break;
            }
        }

    }

    
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public boolean gameOver() {
        return over;
    }
   
    public void reset() {
        board = new int[4][4];
        score = 0;
        previousStates = new LinkedList<>();
        openSpots = 16;
        prevOpenSpots = new ArrayList<>();
        newKey = new boolean[4][4];
        over = false;
        addNewKey();
        addNewKey();
        previousStates.add(new GameStates(copiedArray()));
        prevOpenSpots.add(openSpots);

    }


    
    public int getCell(int c, int r) {
        return board[r][c];
    }

    public boolean checkNewKey(int c, int r) {
        return newKey[r][c];
    }

        public static void main(String[] args) {
    }
}
