package tiffany.hoeung.wordsearch;

import java.util.ArrayList;

public class Puzzle {
    public static ArrayList<Puzzle> puzzles = new ArrayList<>();

    private Character[][] letters;
    private ArrayList<String> words;
    private int levelId;

    public Puzzle(Character[][] letters, ArrayList<String> words, int levelId) {
        this.letters = letters;
        this.words = words;
        this.levelId = levelId;
    }

    public Character[][] getLetters() {
        return letters;
    }

    public ArrayList<String> getWords() {
        return words;
    }

    public static void generatePuzzles() {
        Character[][] newLetters = new Character[8][6];

        // FIRST PUZZLE:
        // First row
        newLetters = parseString("NOMLASTSHARKTUMHUJJUOYWIVCNRPOPACATKXRUZWNTPZALK");

        ArrayList<String> newWords = new ArrayList<>();
        newWords.add("Carp");
        newWords.add("Koi");
        newWords.add("Salmon");
        newWords.add("Shark");
        newWords.add("Trout");
        newWords.add("Tuna");

        Puzzle.puzzles.add(new Puzzle(newLetters, newWords, Puzzle.puzzles.size()+1));

        Level.levels = new ArrayList<>();
        Level.levels.add(new Level(null, null, 6, Puzzle.puzzles.size()));

        // SECOND PUZZLE:
        newLetters = parseString("GNUAPLIIPZIURUMORQAGNEKTFNMVAIFESOSGEPGGNEPANDAR");

        newWords = new ArrayList<>();
        newWords.add("Giraffe");
        newWords.add("Goat");
        newWords.add("Lion");
        newWords.add("Panda");
        newWords.add("Penguin");
        newWords.add("Tiger");

        Puzzle.puzzles.add(new Puzzle(newLetters, newWords, Puzzle.puzzles.size()+1));
        Level.levels.add(new Level(null, null, 6, Puzzle.puzzles.size()));

        // THIRD PUZZLE:
        newLetters = parseString("RHSOPPTONURLEJEANUNATTWTASLCEOLFIYPMPEARTHSNSTJM");

        newWords = new ArrayList<>();
        newWords.add("Earth");
        newWords.add("Meteor");
        newWords.add("Planet");
        newWords.add("Pluto");
        newWords.add("Star");
        newWords.add("Sun");

        Puzzle.puzzles.add(new Puzzle(newLetters, newWords, Puzzle.puzzles.size()+1));
        Level.levels.add(new Level(null, null, 6, Puzzle.puzzles.size()));

        // FOURTH PUZZLE:
        newLetters = parseString("EQRGNHULLRCRCIPAVAUQEPSELPREAPCHERRYIWIKEJJCOBKQ");
        newWords = new ArrayList<>();
        newWords.add("Apple");
        newWords.add("Cherry");
        newWords.add("Grape");
        newWords.add("Kiwi");
        newWords.add("Peach");
        newWords.add("Pear");

        Puzzle.puzzles.add(new Puzzle(newLetters, newWords, Puzzle.puzzles.size()+1));
        Level.levels.add(new Level(null, null, 6, Puzzle.puzzles.size()));

        Level.levels.add(new Level(null, null, 6, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 6, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 6, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 6, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 6, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));


        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 5, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
        Level.levels.add(new Level(null, null, 0, Level.levels.size()+1));
    }

    public static Character[][] parseString(String board) {
        Character[][] letters = new Character[8][6];
        int index = 0;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 6; j++) {
                letters[i][j] = board.charAt(index);
                index++;
            }
        }

        return letters;
    }
}
