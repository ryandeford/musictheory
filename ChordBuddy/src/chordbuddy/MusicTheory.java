package chordbuddy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ryan
 */
public class MusicTheory {

  public static final Character notationFlatChar;
  public static final Character notationSharpChar;
  public static final Map<String, ChordQuality> notationChordQualities;
  public static final String tonicRegex;
  public static final String accidentalRegex;
  public static final String qualityRegex;
  public static final String modifierRegex;

  public static enum NoteName {

    A,
    B,
    C,
    D,
    E,
    F,
    G,
  }

  public static enum ChordQuality {

    Maj,
    Min,
    Dim,
    Aug,
  }
  public static final List<NoteName> orderOfFlats;

  public static enum KeyType {

    Flat,
    Sharp,
    Natural
  }
  public static final Map<NoteName, Key> foundationKeys;

  static {
    notationFlatChar = new Character('b');
    notationSharpChar = new Character('#');

    notationChordQualities = new HashMap<String, ChordQuality>();
    notationChordQualities.put("maj", ChordQuality.Maj);
    notationChordQualities.put("major", ChordQuality.Maj);
    notationChordQualities.put("min", ChordQuality.Min);
    notationChordQualities.put("minor", ChordQuality.Min);
    notationChordQualities.put("-", ChordQuality.Min);
    notationChordQualities.put("dim", ChordQuality.Dim);
    notationChordQualities.put("diminished", ChordQuality.Dim);
    notationChordQualities.put("aug", ChordQuality.Aug);
    notationChordQualities.put("augmented", ChordQuality.Aug);
    notationChordQualities.put("+", ChordQuality.Aug);

    tonicRegex = "(?i)[A-G]";
    accidentalRegex = "(?-i)(?:[b]*|[#]*)";
    qualityRegex = "(?i)(?:maj|major|min|minor|\\-|dim|diminished|aug|augmented|\\+)?";
    modifierRegex = "(?-i)(?:6|7|9|11|13)*(?:(?:[b]+|[#]+)(?:5|7|9|11|13))*";

    orderOfFlats = new ArrayList<NoteName>();
    orderOfFlats.add(NoteName.B);
    orderOfFlats.add(NoteName.E);
    orderOfFlats.add(NoteName.A);
    orderOfFlats.add(NoteName.D);
    orderOfFlats.add(NoteName.G);
    orderOfFlats.add(NoteName.C);
    orderOfFlats.add(NoteName.F);

    foundationKeys = new HashMap<NoteName, Key>();
    foundationKeys.put(NoteName.A, new Key(KeyType.Sharp, 3));
    foundationKeys.put(NoteName.B, new Key(KeyType.Sharp, 5));
    foundationKeys.put(NoteName.C, new Key(KeyType.Natural, 0));
    foundationKeys.put(NoteName.D, new Key(KeyType.Sharp, 2));
    foundationKeys.put(NoteName.E, new Key(KeyType.Sharp, 4));
    foundationKeys.put(NoteName.F, new Key(KeyType.Flat, 1));
    foundationKeys.put(NoteName.G, new Key(KeyType.Sharp, 1));
  }

  public static List<Note> getMajorScale(Note tonic) {
    if (tonic == null) {
      return null;
    }

    List<Note> scale = new ArrayList<Note>();

    for (NoteName n : getScaleSkeleton(tonic)) {
      scale.add(Note.create(n.toString()));
    }

    Key keyInfo = foundationKeys.get(tonic.getLetter());
    for (int i = 0; i < keyInfo.AccidentalCount; i++) {
      MusicTheory.NoteName target = null;
      if (keyInfo.Type == MusicTheory.KeyType.Flat) {
        target = orderOfFlats.get(i % MusicTheory.NoteName.values().length);
      } else {
        // Note: The order of sharps is simply the order of flats reversed
        target = orderOfFlats.get(orderOfFlats.size() - 1 - (i % MusicTheory.NoteName.values().length));
      }

      for (int j = 0; j < scale.size(); j++) {
        if (scale.get(j).getLetter() == target) {
          if (keyInfo.Type == MusicTheory.KeyType.Flat) {
            scale.get(j).flatify(1);
          } else {
            scale.get(j).sharpify(1);
          }

          // Note: Scales are contained within one octave, so there's no need to
          //       further traverse the scale once a target is found
          break;
        }
      }
    }

    // If the tonic contains accidentals, each individual scale degree needs
    // to be updated to reflect the tonic's quality
    if (tonic.getQuality() != 0) {
      for (int i = 0; i < scale.size(); i++) {
        scale.get(i).setQuality(scale.get(i).getQuality() + tonic.getQuality());
      }
    }

    return scale;
  }

  public static List<NoteName> getScaleSkeleton(Note tonic) {
    if (tonic == null) {
      return null;
    }

    List<NoteName> availableNoteNames = Arrays.asList(NoteName.values());
    for (int i = 0; i < availableNoteNames.size(); i++) {
      if (availableNoteNames.indexOf(tonic.getLetter()) == 0) {
        break;
      }

      Collections.rotate(availableNoteNames, -1);
    }

    return availableNoteNames;
  }
}
