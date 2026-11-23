package part2_collection;

import part1_item.Movie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

// Part 2. Collection class built on the base of array
public class MovieCollection {

    // Part 2.7 One specific file name for this program
    public static final String FILE_NAME = "data/movies.txt";

    private Movie[] items = new Movie[0];  // array always has exact length
    private int count = 0;                 // collection manages item count by itself

    // ---------- Helpers ----------

    public int size() {
        return count;
    }

    public boolean isValidIndex(int i) {
        return i >= 0 && i < count;
    }

    // ---------- Part 2.1 Add ----------

    // Increases the array by one after every adding
    public void add(Movie movie) {
        items = Arrays.copyOf(items, count + 1);
        items[count] = movie;
        count++;
    }

    // ---------- Part 2.2 Remove ----------

    // Creates a new array with correct length and copies all items except i
    public boolean remove(int i) {
        if (!isValidIndex(i)) {
            return false;
        }
        Movie[] newItems = new Movie[count - 1];
        int k = 0;
        for (int j = 0; j < count; j++) {
            if (j != i) {
                newItems[k++] = items[j];
            }
        }
        items = newItems;
        count--;
        return true;
    }

    // ---------- Part 2.3 Print ----------

    // Detailed information about one item
    public void printOne(int i) {
        if (!isValidIndex(i)) {
            System.out.println("No movie with number " + (i + 1));
            return;
        }
        System.out.println("#" + (i + 1));
        System.out.println(items[i].getDescription());
    }

    // Detailed information about every item
    public void printAll() {
        if (printIfEmpty()) {
            return;
        }
        for (int i = 0; i < count; i++) {
            printOne(i);
            System.out.println("------------------------------");
        }
    }

    // Numbered list with one-line information
    public void printList() {
        if (printIfEmpty()) {
            return;
        }
        for (int i = 0; i < count; i++) {
            printLine(i);
        }
    }

    private void printLine(int i) {
        System.out.println((i + 1) + ". " + items[i].getTitle());
    }

    private boolean printIfEmpty() {
        if (count == 0) {
            System.out.println("The collection is empty.");
            return true;
        }
        return false;
    }

    // ---------- Part 2.4 Sort ----------

    // Sorts by numeric property: year (older movies first)
    public void sort() {
        Arrays.sort(items, Comparator.comparingInt(Movie::getYear));
    }

    // ---------- Part 2.5 Search by phrase ----------

    // Prints items (with numbers) whose title, director or genre contain the phrase
    public void search(String phrase) {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (items[i].matches(phrase)) {
                printLine(i);
                found++;
            }
        }
        printFoundCount(found);
    }

    // ---------- Part 2.6 Search by year ----------

    public void searchByYear(int year) {
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (items[i].getYear() == year) {
                printLine(i);
                found++;
            }
        }
        printFoundCount(found);
    }

    private void printFoundCount(int found) {
        System.out.println(found == 0 ? "Nothing found." : "Found: " + found);
    }

    // ---------- Part 2.7 Save / read file (bonus) ----------

    // Writes every movie as one line of the file
    public void saveToFile() throws IOException {
        File file = new File(FILE_NAME);
        File folder = file.getParentFile();
        if (folder != null) {
            folder.mkdirs();  // create "data" folder if it does not exist
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (int i = 0; i < count; i++) {
                writer.println(items[i].toFileLine());
            }
        }
    }

    // Replaces the current collection with movies from the file
    public void readFromFile() throws IOException {
        MovieCollection loaded = new MovieCollection();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    loaded.add(Movie.fromFileLine(line));
                }
            }
        }
        // copy only after the whole file was read without errors
        items = loaded.items;
        count = loaded.count;
    }
}
