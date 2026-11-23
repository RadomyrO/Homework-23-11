package part2_collection;

import part1_item.Movie;

import java.io.IOException;

// Part 2. test for collection
public class CollectionDemo {

    public static void main(String[] args) {
        MovieCollection movies = new MovieCollection();

        // Part 2.1
        movies.add(new Movie("Interstellar", "Christopher Nolan", "Sci-Fi", 2014, 8.7));
        movies.add(new Movie("The Godfather", "Francis Ford Coppola", "Crime", 1972, 9.2));
        movies.add(new Movie("Spirited Away", "Hayao Miyazaki", "Animation", 2001, 8.6));
        movies.add(new Movie("Inception", "Christopher Nolan", "Sci-Fi", 2010, 8.8));
        printTitle("Part 2.3 printList() after adding 4 movies, size = " + movies.size());
        movies.printList();

        // Part 2.4
        printTitle("Part 2.4 sort() by year");
        movies.sort();
        movies.printList();

        // Part 2.5
        printTitle("Part 2.5 search(\"nolan\")");
        movies.search("nolan");

        // Part 2.6
        printTitle("Part 2.6 searchByYear(2001)");
        movies.searchByYear(2001);

        // Part 2.3
        printTitle("Part 2.3 printOne(0)");
        movies.printOne(0);

        // Part 2.2
        printTitle("Part 2.2 remove(0) and remove(99)");
        System.out.println("remove(0): " + movies.remove(0));
        System.out.println("remove(99): " + movies.remove(99));
        movies.printList();

        // Part 2.7 only reading here, saving is in Main
        printTitle("Part 2.7 readFromFile()");
        try {
            MovieCollection fromFile = new MovieCollection();
            fromFile.readFromFile();
            fromFile.printList();
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
    }

    private static void printTitle(String title) {
        System.out.println("\n" + title);
    }
}
