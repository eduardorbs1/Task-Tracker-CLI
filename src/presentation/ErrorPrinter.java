package presentation;

public class ErrorPrinter {

    public static void printError(Exception e) {
        IO.println("\u001B[31mError: " + e.getMessage() + "\u001B[0m");
    }
}
