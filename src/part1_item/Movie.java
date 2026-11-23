package part1_item;

import java.time.Year;

// Part 1. Item class
public class Movie {

    // public constants
    public static final int MIN_YEAR = 1888;
    public static final double MAX_RATING = 10.0;
    public static final String FILE_SEPARATOR = ";";

    // private fields
    private String name;
    private String director;
    private String genre;
    private int year;
    private double rating;

    // Part 1.1 constructor
    public Movie(String name, String director, String genre, int year, double rating) {
        setName(name);
        setDirector(director);
        setGenre(genre);
        setYear(year);
        setRating(rating);
    }

    // Part 1.2 getters and setters
    private static String clean(String text) {
        return text.trim().replace(FILE_SEPARATOR, ",");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = clean(name);
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = clean(director);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = clean(genre);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < MIN_YEAR) {
            throw new IllegalArgumentException("Year must be >= " + MIN_YEAR);
        }
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        if (rating < 0 || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be from 0 to " + MAX_RATING);
        }
        this.rating = rating;
    }

    // Part 1.3 short info
    public String getTitle() {
        return String.format("%s (%d) - %s, rating %.1f", name, year, director, rating);
    }

    // Part 1.4 full info
    public String getDescription() {
        return "Title:    " + name + "\n"
             + "Director: " + director + "\n"
             + "Genre:    " + genre + "\n"
             + "Year:     " + year + " (" + getAge(currentYear()) + " years ago)\n"
             + "Rating:   " + String.format("%.1f", rating) + " " + getRatingStars() + "\n"
             + "Classic:  " + (isClassic(currentYear()) ? "yes" : "no");
    }

    // Part 1.5 other methods

    private static int currentYear() {
        return Year.now().getValue();
    }

    public int getAge(int currentYear) {
        return currentYear - year;
    }

    // older than 25 years
    public boolean isClassic(int currentYear) {
        return getAge(currentYear) > 25;
    }

    // 10 points -> 5 stars
    public String getRatingStars() {
        int stars = (int) Math.round(rating / 2);
        return "*".repeat(stars) + "-".repeat(5 - stars);
    }

    // for search
    public boolean matches(String phrase) {
        String p = phrase.toLowerCase();
        return name.toLowerCase().contains(p)
            || director.toLowerCase().contains(p)
            || genre.toLowerCase().contains(p);
    }

    // Part 1.6 for file

    public String toFileLine() {
        return name + FILE_SEPARATOR + director + FILE_SEPARATOR + genre
             + FILE_SEPARATOR + year + FILE_SEPARATOR + rating;
    }

    public static Movie fromFileLine(String line) {
        String[] parts = line.split(FILE_SEPARATOR);
        if (parts.length != 5) {
            throw new IllegalArgumentException("Wrong line format: " + line);
        }
        return new Movie(parts[0], parts[1], parts[2],
                         Integer.parseInt(parts[3].trim()),
                         Double.parseDouble(parts[4].trim()));
    }
}
