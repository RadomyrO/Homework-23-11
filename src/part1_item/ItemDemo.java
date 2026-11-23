package part1_item;

// Part 1. test for Movie
public class ItemDemo {

    public static void main(String[] args) {
        Movie movie = new Movie("Inception", "Christopher Nolan", "Sci-Fi", 2010, 8.8);

        // Part 1.3
        System.out.println("getTitle():");
        System.out.println(movie.getTitle());

        // Part 1.4
        System.out.println("\ngetDescription():");
        System.out.println(movie.getDescription());

        // Part 1.2
        movie.setRating(9.0);
        System.out.println("\nAfter setRating(9.0): " + movie.getTitle());

        // Part 1.5
        System.out.println("matches(\"nolan\"): " + movie.matches("nolan"));
        System.out.println("matches(\"comedy\"): " + movie.matches("comedy"));

        // Part 1.6
        String line = movie.toFileLine();
        System.out.println("toFileLine(): " + line);
        System.out.println("fromFileLine(): " + Movie.fromFileLine(line).getTitle());

        // wrong year
        try {
            movie.setYear(1500);
        } catch (IllegalArgumentException e) {
            System.out.println("setYear(1500) error: " + e.getMessage());
        }
    }
}
