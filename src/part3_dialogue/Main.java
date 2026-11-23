package part3_dialogue;

import part1_item.Movie;
import part2_collection.MovieCollection;

import java.io.IOException;
import java.util.Scanner;

// Part 3. User dialogue
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final MovieCollection movies = new MovieCollection();

    public static void main(String[] args) {
        System.out.println("Welcome to the movie database!");

        // Part 3.1 menu loop
        while (true) {
            printMenu();
            int option = readInt("Your choice: ");
            if (option == 0) {
                System.out.println("Goodbye!");
                break;
            }
            handleOption(option);
        }
    }

    // Part 3.2 menu
    public static void printMenu() {
        System.out.println();
        System.out.println("Choose an option to proceed:");
        System.out.println("1 - print movie list");
        System.out.println("2 - add new movie");
        System.out.println("3 - remove movie");
        System.out.println("4 - sort movies by year");
        System.out.println("5 - search movies by title / director / genre");
        System.out.println("6 - search movies by year");
        System.out.println("7 - print detailed movie list");
        System.out.println("8 - read movies from the file");
        System.out.println("9 - save movies to the file");
        System.out.println("10 - print details of one movie");
        System.out.println("0 - exit the program");
    }

    // Part 3.3
    private static void handleOption(int option) {
        switch (option) {
            case 1 -> movies.printList();
            case 2 -> addMovie();
            case 3 -> removeMovie();
            case 4 -> sortMovies();
            case 5 -> movies.search(readLine("Enter search phrase: "));
            case 6 -> movies.searchByYear(readInt("Enter year: "));
            case 7 -> movies.printAll();
            case 8 -> readFromFile();
            case 9 -> saveToFile();
            case 10 -> movies.printOne(readInt("Enter movie number: ") - 1);
            default -> System.out.println("Unknown option, try again.");
        }
    }

    // Part 3.4 actions

    private static void addMovie() {
        String name = readLine("Title: ");
        String director = readLine("Director: ");
        String genre = readLine("Genre: ");
        int year = readInt("Year: ");
        double rating = readDouble("Rating (0-10): ");
        try {
            movies.add(new Movie(name, director, genre, year, rating));
            System.out.println("Movie added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Movie not added: " + e.getMessage());
        }
    }

    private static void removeMovie() {
        movies.printList();
        int number = readInt("Enter number of the movie to delete: ");
        // -1 because list starts from 1
        if (movies.remove(number - 1)) {
            System.out.println("Movie removed.");
        } else {
            System.out.println("No movie with number " + number);
        }
    }

    private static void sortMovies() {
        movies.sort();
        System.out.println("Movies sorted by year:");
        movies.printList();
    }

    private static void readFromFile() {
        try {
            movies.readFromFile();
            System.out.println("Loaded " + movies.size() + " movies from " + MovieCollection.FILE_NAME);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Cannot read file: " + e.getMessage());
        }
    }

    private static void saveToFile() {
        try {
            movies.saveToFile();
            System.out.println("Saved " + movies.size() + " movies to " + MovieCollection.FILE_NAME);
        } catch (IOException e) {
            System.out.println("Cannot save file: " + e.getMessage());
        }
    }

    // Part 3.5 input

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    // 8,5 also works
    private static double readDouble(String prompt) {
        while (true) {
            try {
                return Double.parseDouble(readLine(prompt).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number, e.g. 8.5");
            }
        }
    }
}
