package chordbuddy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan
 */
public class Chord {

  public static boolean isValidSymbol(String symbol) {
    if (symbol == null) {
      return false;
    }

    symbol = symbol.replaceAll("\\s+", "");

    String tonicRegex = "(?i)[A-G]";
    String accidentalRegex = "(?-i)(?:[b]*|[#]*)";
    String qualityRegex = "(?i)(?:maj|major|min|minor|\\-|dim|diminished|aug|augmented|\\+)?";
    String modifierRegex = "(?-i)(?:6|7|9|11|13)*(?:(?:[b]+|[#]+)(?:5|7|9|11|13))*";

    Pattern validationRegex = Pattern.compile(
            "^" +
            tonicRegex +
            accidentalRegex +
            qualityRegex +
            modifierRegex +
            "$");

    Matcher m = validationRegex.matcher(symbol);
    if (!m.matches()) {
      return false;
    }

    return true;
  }
}