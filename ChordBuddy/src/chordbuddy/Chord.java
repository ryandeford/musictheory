package chordbuddy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan
 */
public class Chord {

  private static final String tonicRegex;
  private static final String accidentalRegex;
  private static final String qualityRegex;
  private static final String modifierRegex;

  static {
    tonicRegex = "(?i)[A-G]";
    accidentalRegex = "(?-i)(?:[b]*|[#]*)";
    qualityRegex = "(?i)(?:maj|major|min|minor|\\-|dim|diminished|aug|augmented|\\+)?";
    modifierRegex = "(?-i)(?:6|7|9|11|13)*(?:(?:[b]+|[#]+)(?:5|7|9|11|13))*";
  }

  public static boolean isValidSymbol(String symbol) {
    if (symbol == null) {
      return false;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern validationPattern = Pattern.compile(
            "^" +
            tonicRegex +
            accidentalRegex +
            qualityRegex +
            modifierRegex +
            "$");

    Matcher m = validationPattern.matcher(symbol);
    if (!m.matches()) {
      return false;
    }

    return true;
  }

  public static String getSymbolTonic(String symbol) {
    if (!Chord.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern tonicPattern = Pattern.compile(
            "^(" +
            tonicRegex +
            accidentalRegex +
            ")" +
            qualityRegex +
            modifierRegex +
            "$");

    Matcher m = tonicPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    return m.group(1);
  }

  public static String getSymbolQuality(String symbol) {
    if (!Chord.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern qualityPattern = Pattern.compile(
            "^" +
            tonicRegex +
            accidentalRegex +
            "(" +
            qualityRegex +
            ")" +
            modifierRegex +
            "$");

    Matcher m = qualityPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    String quality = m.group(1);
    if (quality.equals("")) {
      quality = null;
    }

    return quality;
  }

  public static String getSymbolModifiers(String symbol) {
    if (!Chord.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern modifiersPattern = Pattern.compile(
            "^" +
            tonicRegex +
            accidentalRegex +
            qualityRegex +
            "(" +
            modifierRegex +
            ")$");

    Matcher m = modifiersPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    String modifiers = m.group(1);
    if (modifiers.equals("")) {
      modifiers = null;
    }

    return modifiers;
  }
}
