package chordbuddy;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ryan
 */
public class Chord {

  private Note tonic;
  private MusicTheory.ChordQuality chordQuality;
  private List<Modifier> modifiers;

  private Chord(Note tonic, MusicTheory.ChordQuality quality, List<Modifier> modifiers) {
    this.tonic = tonic;
    this.chordQuality = quality;
    this.modifiers = modifiers;
  }

  public static Chord create(String symbol) {
    if (!Chord.isValidSymbol(symbol)) {
      return null;
    }

    return new Chord(
            Chord.getSymbolTonic(symbol),
            Chord.getSymbolQuality(symbol),
            Chord.getSymbolModifiers(symbol));
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
            MusicTheory.qualityRegex +
            MusicTheory.modifierRegex +
            "$");

    Matcher m = validationPattern.matcher(symbol);
    if (!m.matches()) {
      return false;
    }

    return true;
  }

  public static Note getSymbolTonic(String symbol) {
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

    Note tonic = Note.create(m.group(1));
    return tonic;
  }

  public static MusicTheory.ChordQuality getSymbolQuality(String symbol) {
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

    MusicTheory.ChordQuality quality = MusicTheory.notationChordQualities.get(m.group(1).toLowerCase());

    if (quality == null) {
      Character tonicLetter = symbol.charAt(0);

      if (Character.isLowerCase(tonicLetter)) {
        quality = MusicTheory.ChordQuality.Min;
      } else if (Character.isUpperCase(tonicLetter)) {
        quality = MusicTheory.ChordQuality.Maj;
      }
    }

    return quality;
  }

  public static List<Modifier> getSymbolModifiers(String symbol) {
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

    List<Modifier> modifiers = new ArrayList<Modifier>();

    String modifierString = m.group(1);

    Pattern p = Pattern.compile("(" + MusicTheory.accidentalRegex + ")(\\d+)");
    m = p.matcher(modifierString);
    while (m.find()) {
      String accidentalString = m.group(1);
      String indexString = m.group(2);

      int index = Integer.parseInt(indexString);
      int quality = accidentalString.length();

      if (accidentalString.indexOf(MusicTheory.notationFlatChar) >= 0) {
        quality *= -1;
      }

      modifiers.add(new Modifier(index, quality));
    }

    return modifiers;
  }

  public List<Note> getNotes() {
    SortedMap<Integer, Note> chordTones = new TreeMap<Integer, Note>();
    List<Note> scale = MusicTheory.getMajorScale(this.tonic);

    Note root = Note.copy(scale.get(0));
    Note third = Note.copy(scale.get(2));
    Note fifth = Note.copy(scale.get(4));

    if (this.chordQuality == MusicTheory.ChordQuality.Min) {
      third.flatify(1);
    } else if (this.chordQuality == MusicTheory.ChordQuality.Dim) {
      third.flatify(1);
      fifth.flatify(1);
    } else if (this.chordQuality == MusicTheory.ChordQuality.Aug) {
      fifth.sharpify(1);
    } else if (this.chordQuality == MusicTheory.ChordQuality.Dom) {
      Note seventh = Note.copy(scale.get(6));
      seventh.flatify(1);
      chordTones.put(7, seventh);
    } else if (this.chordQuality == MusicTheory.ChordQuality.Sus2) {
      chordTones.put(2, Note.copy(scale.get(1)));
    } else if (this.chordQuality == MusicTheory.ChordQuality.Sus4) {
      chordTones.put(4, Note.copy(scale.get(3)));
    }

    chordTones.put(1, root);
    if (this.chordQuality != MusicTheory.ChordQuality.Sus2 && this.chordQuality != MusicTheory.ChordQuality.Sus4) {
      chordTones.put(3, third);
    }
    chordTones.put(5, fifth);

    for (Modifier modifier : this.modifiers) {
      Note target = Note.copy(scale.get((modifier.TargetIndex - 1) % scale.size()));

      if (modifier.Quality == 0) {
        // this is just an addition w/o modification
        if (modifier.TargetIndex == 7) {
          if (this.chordQuality == MusicTheory.ChordQuality.Min) {
            target.flatify(1);
          } else if (this.chordQuality == MusicTheory.ChordQuality.Dim) {
            target.flatify(2);
          } else if (this.chordQuality == MusicTheory.ChordQuality.Dom) {
            continue;
          }
        }
      } else if (modifier.TargetIndex == 5) {
        fifth = chordTones.get(5);
        fifth.setQuality(fifth.getQuality() + modifier.Quality);
      }

      target.setQuality(target.getQuality() + modifier.Quality);
      chordTones.put(modifier.TargetIndex, target);
    }

    return new ArrayList<Note>(chordTones.values());
  }
}
