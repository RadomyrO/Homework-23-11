package part1_item;

import java.time.Year;

// Part 1. Item class: Movie
// One hobby object (item) of the database.
public class Movie {

    // ---------- Public members ----------

    // Public constants: allowed limits for numeric properties
    public static final int MIN_YEAR = 1888;      // year of the first film ever
    public static final double MAX_RATING = 10.0;

    // Separator used when the movie is written to the file
    public static final String FILE_SEPARATOR = ";";

    // ---------- Private members ----------

    private String name;       // movie title, e.g. "Inception"
    private String director;   // director's name
    private String genre;      // genre, e.g. "Sci-Fi"
    private int year;          // numeric property: release year
    private double rating;     // numeric property: rating 0..10

    // Part 1.1 Constructor
    public Movie(String name, String director, String genre, int year, double rating) {
        setName(name);
        setDirector(director);
        setGenre(genre);
        setYear(year);
        setRating(rating);
    }

    // Part 1.2 Getters and setters for private properties

    // Removes spaces at the ends and the file separator, so the file never breaks
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
        // do not allow impossible years
        if (year < MIN_YEAR) {
            throw new IllegalArgumentException("Year must be >= " + MIN_YEAR);
        }
        this.year = year;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        // rating must be inside 0..10
        if (rating < 0 || rating > MAX_RATING) {
            throw new IllegalArgumentException("Rating must be from 0 to " + MAX_RATING);
        }
        this.rating = rating;
    }

    // Part 1.3 getTitle(): short one-line description
    public String getTitle() {
        return String.format("%s (%d) - %s, rating %.1f", name, year, director, rating);
    }

    // Part 1.4 getDescription(): detailed multi-line description
    public String getDescription() {
        return "Title:    " + name + "\n"
             + "Director: " + director + "\n"
             + "Genre:    " + genre + "\n"
             + "Year:     " + year + " (" + getAge(currentYear()) + " years ago)\n"
             + "Rating:   " + String.format("%.1f", rating) + " " + getRatingStars() + "\n"
             + "Classic:  " + (isClassic(currentYear()) ? "yes" : "no");
    }

    // Part 1.5 Other methods to handle / calculate item data

    // Current year from the system clock
    private static int currentYear() {
        return Year.now().getValue();
    }

    // How many years passed since the release
    public int getAge(int currentYear) {
        return currentYear - year;
    }

    // A movie is "classic" if it is older than 25 years
    public boolean isClassic(int currentYear) {
        return getAge(currentYear) > 25;
    }

    // Rating as 5 stars, e.g. 8.6 -> "****-"
    public String getRatingStars() {
        int stars = (int) Math.round(rating / 2);  // 10-point scale -> 5 stars
        return "*".repeat(stars) + "-".repeat(5 - stars);
    }

    // Does the movie contain the phrase in title, director or genre (ignoring case)
    public boolean matches(String phrase) {
        String p = phrase.toLowerCase();
        return name.toLowerCase().contains(p)
            || director.toLowerCase().contains(p)
            || genre.toLowerCase().contains(p);
    }

    // Part 1.6 Conversion to / from one line of the file

    // Movie -> "Inception;Christopher Nolan;Sci-Fi;2010;8.8"
    public String toFileLine() {
        return name + FILE_SEPARATOR + director + FILE_SEPARATOR + genre
             + FILE_SEPARATOR + year + FILE_SEPARATOR + rating;
    }

    // "Inception;Christopher Nolan;Sci-Fi;2010;8.8" -> Movie
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
