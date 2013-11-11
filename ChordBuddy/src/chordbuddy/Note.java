package chordbuddy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan
 */
public class Note {

  private MusicTheory.NoteName letter;
  private Integer quality;

  private Note(MusicTheory.NoteName letter, Integer quality) {
    this.letter = letter;
    this.quality = quality;
  }

  public static Note create(String symbol) {
    if (!Note.isValidSymbol(symbol)) {
      return null;
    }

    return new Note(Note.getSymbolLetter(symbol), Note.getSymbolQuality(symbol));
  }

  public static Note copy(Note note) {
    if (note == null) {
      return null;
    }

    Note copy = new Note(note.letter, note.quality);

    return copy;
  }

  public static boolean isValidSymbol(String symbol) {
    if (symbol == null) {
      return false;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern validationPattern = Pattern.compile(
            "^" +
            MusicTheory.tonicRegex +
            MusicTheory.accidentalRegex +
            "$");

    Matcher m = validationPattern.matcher(symbol);
    if (!m.matches()) {
      return false;
    }

    return true;
  }

  public static MusicTheory.NoteName getSymbolLetter(String symbol) {
    if (!Note.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern letterPattern = Pattern.compile(
            "^(" +
            MusicTheory.tonicRegex +
            ")" +
            MusicTheory.accidentalRegex +
            "$");

    Matcher m = letterPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    String letter = m.group(1).toUpperCase();
    return MusicTheory.NoteName.valueOf(letter);
  }

  public static Integer getSymbolQuality(String symbol) {
    if (!Note.isValidSymbol(symbol)) {
      return null;
    }

    symbol = Utilities.removeWhitespace(symbol);

    Pattern qualityPattern = Pattern.compile(
            "^" +
            MusicTheory.tonicRegex +
            "(" +
            MusicTheory.accidentalRegex +
            ")$");

    Matcher m = qualityPattern.matcher(symbol);
    if (!m.matches()) {
      return null;
    }

    String quality = m.group(1);
    int qualityMagnitude = quality.length();

    if (quality.indexOf(MusicTheory.notationFlatChar) >= 0) {
      qualityMagnitude *= -1;
    }

    return qualityMagnitude;
  }

  public MusicTheory.NoteName getLetter() {
    return this.letter;
  }

  public Integer getQuality() {
    return this.quality;
  }

  public void setQuality(Integer quality) {
    this.quality = quality;
  }

  public String getFullName() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.letter);

    Character accidental = new Character('\0');
    if (this.quality < 0) {
      accidental = MusicTheory.notationFlatChar;
    } else if (this.quality > 0) {
      accidental = MusicTheory.notationSharpChar;
    }

    for (int i = 0; i < Math.abs(this.quality); i++) {
      sb.append(accidental);
    }

    return sb.toString();
  }

  public void flatify(int n) {
    this.quality -= Math.abs(n);
  }

  public void sharpify(int n) {
    this.quality += Math.abs(n);
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (!(other instanceof Note)) {
      return false;
    }

    Note otherNote = (Note) other;
    return this.letter.equals(otherNote.letter) &&
            this.quality == otherNote.quality;
  }

  @Override
  public String toString() {
    return this.getFullName();
  }
}
