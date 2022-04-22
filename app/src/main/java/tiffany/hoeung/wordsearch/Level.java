package tiffany.hoeung.wordsearch;

import java.util.ArrayList;

public class Level {
    public static ArrayList<Level> levels = new ArrayList<Level>();

    private ArrayList<xyPositions[]> wordIndices;
    private ArrayList<String> words;
    private int wordsComplete;
    private int levelId;

    public Level(ArrayList<xyPositions[]> wordIndices, ArrayList<String> words,
                 int wordsComplete, int levelId) {
        this.wordIndices = wordIndices;
        this.words = words;
        this.wordsComplete = wordsComplete;
        this.levelId = levelId;
    }

    public int getWordsComplete() { return wordsComplete; }

    public int getLevelId() { return levelId; }

    public ArrayList<String> getWords() { return words; }

    public ArrayList<xyPositions[]> getWordIndices() { return wordIndices; }
}
