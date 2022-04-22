package tiffany.hoeung.wordsearch;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class GameFragment extends Fragment {

    /******View References*********/
    private TextView[][] boardTV;
    private TextView[] wordTV;

    private Boolean[][] selectedTV;
    private xyPositions currSelected;
    private Character[][] letters;
    private ArrayList<String> words;
    private Random rand = new Random();

    private int unselectedClr;
    private int selectedClr;
    private int[] correctClrs;

    // Keeps track of found solutions
    private xyPositions[][] foundSolutions;
    private int solutionCount;
    private int wordIndex;

    private int completedLevels = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        initalizeBoardTVs(view);
        initializeGame(completedLevels);
        return view;
    }

    private void initializeGame(int index) {
        initializeLetters(index);

        // Coloring:
        unselectedClr = R.color.textColor;
        selectedClr = R.color.red;
        correctClrs = new int[6];
        correctClrs[0] = R.drawable.letterbackground;
        correctClrs[1] = R.drawable.letterbackground2;
        correctClrs[2] = R.drawable.letterbackground3;
        correctClrs[3] = R.drawable.letterbackground4;
        correctClrs[4] = R.drawable.letterbackground5;
        correctClrs[5] = R.drawable.letterbackground6;

        // For solutions:
        foundSolutions = new xyPositions[words.size()][2];
        solutionCount = 0;

        setBoardTVs();
    }

    private void setBoardTVs() {
        for (int i = 0; i < 8; i++) {
            for (int y = 0; y < 6; y++) {
                boardTV[i][y].setText(letters[i][y].toString());
                int indexI = i;
                int indexY = y;
                boardTV[i][y].setOnClickListener(view1 -> {
                    toggleLetter(indexI, indexY);
                });
            }
        }

        for(int i = 0; i < wordTV.length; i++)
            wordTV[i].setText(words.get(i));
    }

    private void toggleLetter(int x, int y) {
        // Check if this is our first letter:
        if(currSelected == null) {
            // It is, so select it:
            selectLetter(x, y);
        } else {
            // Check if are trying to disable our first letter
            if(currSelected.compareTo(new xyPositions(x, y)) == 0) {
                disableLetter(x, y);
            } else {
                // This is the second letter we are selecting.
                // Check if the word is valid
                String word = getWord(x, y);
                if(word.compareTo("") != 0) {
                    // The word is valid; check if this exists in the word list
                    System.out.println(word);
                    if(checkWord(word)) {
                        markFound(word, x, y);
                        disableLetter(currSelected.getx(), currSelected.gety());
                    } else
                        disableLetter(currSelected.getx(), currSelected.gety());
                } else {
                    // The indexes are not valid ; reset
                    disableLetter(currSelected.getx(), currSelected.gety());
                }
            }
        }
    }

    private void markFound(String word, int x, int y) {
        foundSolutions[solutionCount][0] = new xyPositions(currSelected.getx(), currSelected.gety());
        foundSolutions[solutionCount][1] = new xyPositions(x, y);
        // positions of the letters:
        xyPositions[] positions = getPositions(currSelected.getx(), currSelected.gety(), x, y);

        // Mark the solution as blue
        for(int i = 0; i < positions.length; i++) {
            int thisX = positions[i].getx();
            int thisY = positions[i].gety();
            boardTV[thisX][thisY].setBackground(getResources().getDrawable(correctClrs[solutionCount]));
        }

        // Change the word at the bottom to be crossed out
        wordTV[wordIndex].setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        // Increase solution counti
        solutionCount++;
        if(solutionCount == words.size()) {
            levelComplete();
        }
    }

    private void levelComplete() {
        // When level is complete, we want to display a "congrats, you did it! next level?"
        // "Next Level" or "Main Menu" buttons
        // For now, we initialize the next puzzle
        resetBoard();
        completedLevels++;
        if(completedLevels < Puzzle.puzzles.size()) {
            resetBoard();
            initializeGame(completedLevels);
        } else
            System.out.println("You did it!");
    }

    private void resetBoard() {
        // For each word:
        for(int i = 0; i < words.size(); i++) {
            // Reset the letters on the board to no color
            xyPositions pos1 = foundSolutions[i][0];
            xyPositions pos2 = foundSolutions[i][1];
            xyPositions[] xyPos = getPositions(pos1.getx(), pos1.gety(), pos2.getx(), pos2.gety());
            for(int j = 0; j < xyPos.length; j++)
                boardTV[xyPos[j].getx()][xyPos[j].gety()].setBackground(getResources().getDrawable(R.drawable.blank));

            // Reset the words to not be crossed
            wordTV[i].setPaintFlags(0);
        }
    }

    private void disableLetter(int x, int y) {
        currSelected = null;
        selectedTV[x][y] = false;
        boardTV[x][y].setTextColor(getResources().getColor(unselectedClr));
    }

    private void selectLetter(int x, int y) {
        currSelected = new xyPositions(x, y);
        selectedTV[x][y] = true;
        boardTV[x][y].setTextColor(getResources().getColor(selectedClr));
    }

    private boolean checkWord(String word) {
        for (String entry : words) {
            if(entry.toUpperCase().compareTo(word) == 0) {
                wordIndex = words.indexOf(entry);
                return true;
            }

            // Check if it works backwords
            StringBuilder str = new StringBuilder();
            for(int i = word.length()-1; i >= 0; i--)
                str.append(word.charAt(i));
            if(entry.toUpperCase().compareTo(str.toString()) == 0) {
                wordIndex = words.indexOf(entry);
                return true;
            }
        }
        return false;
    }

    private String getWord(int xTo, int yTo) {
        xyPositions[] positions = getPositions(currSelected.getx(), currSelected.gety(), xTo, yTo);
        StringBuilder str = new StringBuilder();
        for(int i = 0; i < positions.length; i++) {
            str.append(letters[positions[i].getx()][positions[i].gety()]);
        }
        return str.toString();
    }

    private xyPositions[] getPositions(int xFrom, int yFrom, int xTo, int yTo) {
        int currX = xFrom, currY = yFrom;
        xyPositions[] fromToPositions = new xyPositions[0];
        int count = 0;
        // Cases:
        if(xFrom == xTo) { // Exists in the same row
            int difference = yFrom - yTo;
            if(difference < 0)
                difference *= -1;
            fromToPositions = new xyPositions[difference+1];

            // y1 should be able to increase or decrease to y2
            if(yFrom > yTo) { // Decrease
                while(currY+1 != yTo) {
                    fromToPositions[count] = new xyPositions(currX, currY);
                    count++;
                    currY--;
                }
            } else { // Increase
                while(currY-1 != yTo) {
                    fromToPositions[count] = new xyPositions(currX, currY);
                    count++;
                    currY++;
                }
            }
        } else if (yFrom == yTo) { // Exists in the same column
            int difference = xFrom - xTo;
            if(difference < 0)
                difference *= -1;
            fromToPositions = new xyPositions[difference+1];

            // y1 should be able to increase or decrease to y2
            if(xFrom > xTo) { // Decrease
                while(currX+1 != xTo) {
                    fromToPositions[count] = new xyPositions(currX, currY);
                    count++;
                    currX--;
                }
            } else { // Increase
                while(currX-1 != xTo) {
                    fromToPositions[count] = new xyPositions(currX, currY);
                    count++;
                    currX++;
                }
            }
        } else { // We have a diagonal case
            int xMinus = (xFrom - xTo);
            int yMinus = (yFrom - yTo);
            if(yMinus < 0)
                yMinus *= -1;
            if(xMinus < 0)
                xMinus *= -1;

            if (xMinus == yMinus) {
                fromToPositions = new xyPositions[xMinus+1];
                if(xFrom > xTo) {
                    if(yFrom > yTo) {
                        while(currX+1 != xTo) {
                            fromToPositions[count] = new xyPositions(currX, currY);
                            count++;
                            currX--;
                            currY--;
                        }
                    } else {
                        while(currX+1 != xTo) {
                            fromToPositions[count] = new xyPositions(currX, currY);
                            count++;
                            currX--;
                            currY++;
                        }
                    }
                } else {
                    if(yFrom > yTo) {
                        while(currX-1 != xTo) {
                            fromToPositions[count] = new xyPositions(currX, currY);
                            count++;
                            currX++;
                            currY--;
                        }
                    } else {
                        while(currX-1 != xTo) {
                            fromToPositions[count] = new xyPositions(currX, currY);
                            count++;
                            currX++;
                            currY++;
                        }
                    }
                }
            } else {
                System.out.println("This ain't no diagonal you dumb fucking cunt stupid piece of shit maidenless ugly ass doo doo head");
            }
        }

        return fromToPositions;
    }

    /**********INITIALIZING METHODS*********************/

    private void initializeLetters(int index) {
        // Generate the puzzles
        if(Puzzle.puzzles.size() == 0)
            Puzzle.generatePuzzles();
        // Get the puzzle we want to display
        Puzzle puzzle = Puzzle.puzzles.get(index);
        // The letters and words we want to display
        letters = puzzle.getLetters();
        words = puzzle.getWords();
    }

    private void initalizeBoardTVs(View view) {
        boardTV = new TextView[8][6];

        // First Row
        boardTV[0][0] = view.findViewById(R.id.board_1x1);
        boardTV[0][1] = view.findViewById(R.id.board_1x2);
        boardTV[0][2] = view.findViewById(R.id.board_1x3);
        boardTV[0][3] = view.findViewById(R.id.board_1x4);
        boardTV[0][4] = view.findViewById(R.id.board_1x5);
        boardTV[0][5] = view.findViewById(R.id.board_1x6);

        // Second Row
        boardTV[1][0] = view.findViewById(R.id.board_2x1);
        boardTV[1][1] = view.findViewById(R.id.board_2x2);
        boardTV[1][2] = view.findViewById(R.id.board_2x3);
        boardTV[1][3] = view.findViewById(R.id.board_2x4);
        boardTV[1][4] = view.findViewById(R.id.board_2x5);
        boardTV[1][5] = view.findViewById(R.id.board_2x6);

        // Third Row
        boardTV[2][0] = view.findViewById(R.id.board_3x1);
        boardTV[2][1] = view.findViewById(R.id.board_3x2);
        boardTV[2][2] = view.findViewById(R.id.board_3x3);
        boardTV[2][3] = view.findViewById(R.id.board_3x4);
        boardTV[2][4] = view.findViewById(R.id.board_3x5);
        boardTV[2][5] = view.findViewById(R.id.board_3x6);

        // Fourth Row
        boardTV[3][0] = view.findViewById(R.id.board_4x1);
        boardTV[3][1] = view.findViewById(R.id.board_4x2);
        boardTV[3][2] = view.findViewById(R.id.board_4x3);
        boardTV[3][3] = view.findViewById(R.id.board_4x4);
        boardTV[3][4] = view.findViewById(R.id.board_4x5);
        boardTV[3][5] = view.findViewById(R.id.board_4x6);

        // Fifth Row
        boardTV[4][0] = view.findViewById(R.id.board_5x1);
        boardTV[4][1] = view.findViewById(R.id.board_5x2);
        boardTV[4][2] = view.findViewById(R.id.board_5x3);
        boardTV[4][3] = view.findViewById(R.id.board_5x4);
        boardTV[4][4] = view.findViewById(R.id.board_5x5);
        boardTV[4][5] = view.findViewById(R.id.board_5x6);

        // Sixth Row
        boardTV[5][0] = view.findViewById(R.id.board_6x1);
        boardTV[5][1] = view.findViewById(R.id.board_6x2);
        boardTV[5][2] = view.findViewById(R.id.board_6x3);
        boardTV[5][3] = view.findViewById(R.id.board_6x4);
        boardTV[5][4] = view.findViewById(R.id.board_6x5);
        boardTV[5][5] = view.findViewById(R.id.board_6x6);

        // Seventh Row
        boardTV[6][0] = view.findViewById(R.id.board_7x1);
        boardTV[6][1] = view.findViewById(R.id.board_7x2);
        boardTV[6][2] = view.findViewById(R.id.board_7x3);
        boardTV[6][3] = view.findViewById(R.id.board_7x4);
        boardTV[6][4] = view.findViewById(R.id.board_7x5);
        boardTV[6][5] = view.findViewById(R.id.board_7x6);

        // Eighth Row
        boardTV[7][0] = view.findViewById(R.id.board_8x1);
        boardTV[7][1] = view.findViewById(R.id.board_8x2);
        boardTV[7][2] = view.findViewById(R.id.board_8x3);
        boardTV[7][3] = view.findViewById(R.id.board_8x4);
        boardTV[7][4] = view.findViewById(R.id.board_8x5);
        boardTV[7][5] = view.findViewById(R.id.board_8x6);

        selectedTV = new Boolean[8][6];
        for(int i = 0; i < 8; i++) {
            for(int y = 0; y < 6; y++)
                selectedTV[i][y] = false;
        }

        wordTV = new TextView[6];
        wordTV[0] = view.findViewById(R.id.word1);
        wordTV[1] = view.findViewById(R.id.word2);
        wordTV[2] = view.findViewById(R.id.word3);
        wordTV[3] = view.findViewById(R.id.word4);
        wordTV[4] = view.findViewById(R.id.word5);
        wordTV[5] = view.findViewById(R.id.word6);
    }
}

class xyPositions {
    private int x;
    private int y;

    xyPositions(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getx() {
        return x;
    }

    public int gety() {
        return y;
    }

    public int compareTo(xyPositions xy) {
        int rtn = 0;
        if (x == xy.getx() && y == xy.gety()) {
            rtn = 0;
        } else {
            rtn = -1;
        }
        return rtn;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("(" + x + ", " + y + ")");
        return str.toString();
    }
}