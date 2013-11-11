package chordbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Ryan
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("+++ Welcome to ChordBuddy +++");
      System.out.print("Enter a chord symbol: ");

      BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
      String symbol;

      try {
        while ((symbol = stdin.readLine()) != null && symbol.length() != 0) {
          Chord chord = Chord.create(symbol);

          if (chord != null) {
            System.out.println(" => " + chord.getNotes());
          } else {
            System.err.println(String.format("Invalid chord symbol detected!: '%s'", symbol));
          }

          System.out.print("More?...or hit enter to exit: ");
        }

        stdin.close();
        System.out.println("Goodbye!");
      } catch (IOException e) {
        System.err.println("An error occurred while reading symbol input: " + e.getMessage());
      }
    }
  }
}
