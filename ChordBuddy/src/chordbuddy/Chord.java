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

    symbol = Utilities.removeWhitespace(symbol);

    Pattern validationPattern = Pattern.compile(
            "^" +
            MusicTheory.tonicRegex +
            MusicTheory.accidentalRegex +
            MusicTheory.qualityRegex +
            MusicTheory.modifierRegex +
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
            MusicTheory.tonicRegex +
            MusicTheory.accidentalRegex +
            ")" +
            MusicTheory.qualityRegex +
            MusicTheory.modifierRegex +
            "$");

    Matcher m = tonicPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    String tonic = m.group(1);
    return tonic;
  }

  public static String getSymbolQuality(String symbol) {
    if (!Chord.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern qualityPattern = Pattern.compile(
            "^" +
            MusicTheory.tonicRegex +
            MusicTheory.accidentalRegex +
            "(" +
            MusicTheory.qualityRegex +
            ")" +
            MusicTheory.modifierRegex +
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
            MusicTheory.tonicRegex +
            MusicTheory.accidentalRegex +
            MusicTheory.qualityRegex +
            "(" +
            MusicTheory.modifierRegex +
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
