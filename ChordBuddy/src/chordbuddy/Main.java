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
          if (Chord.isValidSymbol(symbol)) {
            System.out.println("\tTonic: " + Chord.getSymbolTonic(symbol));
            System.out.println("\tQuality: " + Chord.getSymbolQuality(symbol));
            System.out.println("\tModifiers: " + Chord.getSymbolModifiers(symbol));
          } else {
            System.err.println(String.format("Invalid chord symbol detected!: '%s'", symbol));
          }

          System.out.print("More? (hit enter to exit): ");
        }

        stdin.close();
        System.out.println("Goodbye!");
      } catch (IOException e) {
        System.err.println("An error occurred while reading symbol input: " + e.getMessage());
      }
    }
  }
}
