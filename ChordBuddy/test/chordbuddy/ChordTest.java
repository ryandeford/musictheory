package chordbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ryan
 */
public class ChordTest {

  private static final List<String> tonics;
  private static final Set<String> qualities;
  private static final List<Integer> premodifiers;
  private static final List<Integer> postmodifiers;
  private static final Character flatChar;
  private static final Character sharpChar;

  static {
    tonics = new ArrayList<String>();
    tonics.add("A");
    tonics.add("B");
    tonics.add("C");
    tonics.add("D");
    tonics.add("E");
    tonics.add("F");
    tonics.add("G");

    qualities = new HashSet<String>();
    qualities.addAll(MusicTheory.notationChordQualities.keySet());

    premodifiers = new ArrayList<Integer>();
    premodifiers.add(6);
    premodifiers.add(7);
    premodifiers.add(9);
    premodifiers.add(11);
    premodifiers.add(13);

    postmodifiers = new ArrayList<Integer>();
    postmodifiers.add(5);
    postmodifiers.add(7);
    postmodifiers.add(9);
    postmodifiers.add(11);
    postmodifiers.add(13);

    flatChar = 'b';
    sharpChar = '#';
  }

  @Test
  public void testIsValidSymbol_null() {
    assertTrue("A null string is not a valid chord symbol", !Chord.isValidSymbol(null));
  }

  @Test
  public void testIsValidSymbol_empty() {
    assertTrue("An empty string is not a valid chord symbol", !Chord.isValidSymbol(""));
  }

  @Test
  public void testIsValidSymbol_tonics() {
    for (String tonic : tonics) {
      String symbol = tonic.toUpperCase();
      assertTrue(String.format("A tonic without any accidentals is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_tonicFlats() {
    String symbol = tonics.get(0);

    for (int i = 0; i < 3; i++) {
      symbol += flatChar;
      assertTrue(String.format("A tonic with 1 or more flats is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_tonicSharps() {
    String symbol = tonics.get(0);

    for (int i = 0; i < 3; i++) {
      symbol += sharpChar;
      assertTrue(String.format("A tonic with 1 or more sharps is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_qualities() {
    String tonic = tonics.get(0);

    for (String quality : qualities) {
      String symbol = tonic + quality;
      assertTrue(String.format("A tonic with a chord quality is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_premodifiers() {
    String tonic = tonics.get(0);

    for (Integer premodifier : premodifiers) {
      String symbol = tonic + premodifier;
      assertTrue(String.format("A tonic with an appropriate premodifier is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_postmodifiers() {
    String tonic = tonics.get(0);
    String quality = qualities.iterator().next();
    Character accidental = sharpChar;

    for (Integer postmodifier : postmodifiers) {
      String symbol = tonic + quality + accidental + postmodifier;
      assertTrue(String.format("A qualified chord with an appropriate postmodifier is a valid chord symbol: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_startsWithLetter() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("1A");
    symbols.add("Hmajor");
    symbols.add("min7");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol must start with an appropriate letter: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_singleTonicAllowed() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("AAmaj");
    symbols.add("AminB");
    symbols.add("CBdim");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol must only contain a single tonic: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_whitespace() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("C major");
    symbols.add("G # m i n");
    symbols.add("A-7 b5");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol may contain whitespace: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_mixedTonicAccidentals() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("Ab#");
    symbols.add("E##b");
    symbols.add("Cbb#");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol cannot contain mixed accidentals on its tonic: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_mixedModifierAccidentals() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("Bmin7b5#b9");
    symbols.add("Daugbb#7");
    symbols.add("Gmaj7##9bb#13");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol cannot contain mixed accidentals on its modifiers: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_caseInsensitiveTonics() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("amin7");
    symbols.add("Bmaj6");
    symbols.add("f");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol tonic may be provided in any casing: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_caseInsensitiveQualities() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("aMiNoR7");
    symbols.add("BMAJ");
    symbols.add("fbdIm");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol quality may be provided in any casing: '%s'", symbol), Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol_caseSensitiveAccidentals() {
    Set<String> symbols = new HashSet<String>();
    symbols.add("ABminor");
    symbols.add("BB6");
    symbols.add("Db7b5#9B13");

    for (String symbol : symbols) {
      assertTrue(String.format("A chord symbol must contain lowercase accidentals: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testIsValidSymbol() {
    Set<String> symbolsValid = new HashSet<String>();
    symbolsValid.add("A major");
    symbolsValid.add("B-7b5");
    symbolsValid.add("Dbbbdim7");
    symbolsValid.add("C#6b9b13");
    symbolsValid.add("E");
    symbolsValid.add("F + 7 #11 b13");
    symbolsValid.add("gmin");
    symbolsValid.add("A7");
    symbolsValid.add("D#9");
    symbolsValid.add("Fbb13");

    for (String symbol : symbolsValid) {
      assertTrue(String.format("The following chord symbol should be valid: '%s'", symbol), Chord.isValidSymbol(symbol));
    }

    Set<String> symbolsInvalid = new HashSet<String>();
    symbolsInvalid.add("G monir");
    symbolsInvalid.add("F#minormajor");
    symbolsInvalid.add("Emin0");
    symbolsInvalid.add("Dbbdimb10");
    symbolsInvalid.add("C3");
    symbolsInvalid.add("Bmaj7sharp11");
    symbolsInvalid.add("AB");

    for (String symbol : symbolsInvalid) {
      assertTrue(String.format("The following chord symbol should be invalid: '%s'", symbol), !Chord.isValidSymbol(symbol));
    }
  }

  @Test
  public void testGetSymbolTonic_null() {
    Note expected = null;
    Note actual = Chord.getSymbolTonic(null);

    assertEquals("A null symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic_empty() {
    Note expected = null;
    Note actual = Chord.getSymbolTonic("");

    assertEquals("An empty symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic_invalid() {
    Note expected = null;
    Note actual = Chord.getSymbolTonic("badchord");

    assertEquals("An invalid symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic() {
    Map<String, Note> symbols = new HashMap<String, Note>();
    symbols.put("Amaj", Note.create("A"));
    symbols.put("B bmin", Note.create("Bb"));
    symbols.put("C", Note.create("C"));
    symbols.put("D###", Note.create("D###"));
    symbols.put("E b b Major7 #11", Note.create("Ebb"));
    symbols.put("fmin11", Note.create("f"));
    symbols.put("G-6", Note.create("G"));
    symbols.put("C#dom7", Note.create("C#"));
    symbols.put("A7", Note.create("A"));
    symbols.put("B#9", Note.create("B#"));
    symbols.put("Cb13", Note.create("Cb"));
    symbols.put("D##9", Note.create("D##"));
    symbols.put("Ebb11", Note.create("Ebb"));
    symbols.put("Fbb#11", Note.create("Fbb"));
    symbols.put("G##b13", Note.create("G##"));

    for (String symbol : symbols.keySet()) {
      Note tonic = Chord.getSymbolTonic(symbol);

      Note expected = symbols.get(symbol);
      Note actual = tonic;

      assertEquals(String.format("A valid symbol should return the chord's tonic: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetSymbolQuality_null() {
    MusicTheory.ChordQuality expected = null;
    MusicTheory.ChordQuality actual = Chord.getSymbolQuality(null);

    assertEquals("A null symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_empty() {
    MusicTheory.ChordQuality expected = null;
    MusicTheory.ChordQuality actual = Chord.getSymbolQuality("");

    assertEquals("An empty symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_invalid() {
    MusicTheory.ChordQuality expected = null;
    MusicTheory.ChordQuality actual = Chord.getSymbolQuality("badchord");

    assertEquals("An invalid symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality() {
    Map<String, MusicTheory.ChordQuality> symbols = new HashMap<String, MusicTheory.ChordQuality>();
    symbols.put("Amaj", MusicTheory.ChordQuality.Maj);
    symbols.put("Bbmin7", MusicTheory.ChordQuality.Min);
    symbols.put("Cdim7b5", MusicTheory.ChordQuality.Dim);
    symbols.put("Daugb9#11", MusicTheory.ChordQuality.Aug);
    symbols.put("Ebb Ma j or7 #11", MusicTheory.ChordQuality.Maj);
    symbols.put("fmin11", MusicTheory.ChordQuality.Min);
    symbols.put("G-6", MusicTheory.ChordQuality.Min);
    symbols.put("a+7#11", MusicTheory.ChordQuality.Aug);
    symbols.put("B diminished", MusicTheory.ChordQuality.Dim);
    symbols.put("C", MusicTheory.ChordQuality.MajInferred);
    symbols.put("c", MusicTheory.ChordQuality.Min);

    for (String symbol : symbols.keySet()) {
      MusicTheory.ChordQuality quality = Chord.getSymbolQuality(symbol);

      MusicTheory.ChordQuality expected = symbols.get(symbol);
      MusicTheory.ChordQuality actual = quality;

      assertEquals(String.format("A valid symbol should return the chord's quality: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetSymbolModifiers_null() {
    List<Modifier> expected = null;
    List<Modifier> actual = Chord.getSymbolModifiers(null);

    assertEquals("A null symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers_empty() {
    List<Modifier> expected = null;
    List<Modifier> actual = Chord.getSymbolModifiers("");

    assertEquals("An empty symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers_invalid() {
    List<Modifier> expected = null;
    List<Modifier> actual = Chord.getSymbolModifiers("badchord");

    assertEquals("An invalid symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers() {
    Map<String, List<Modifier>> symbols = new HashMap<String, List<Modifier>>();
    List<Modifier> output;

    output = new ArrayList<Modifier>();
    symbols.put("Amaj", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(7, 0));
    symbols.put("Bbmin7", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(7, 0));
    output.add(new Modifier(5, -1));
    symbols.put("Cdim7b5", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(9, -1));
    output.add(new Modifier(11, 1));
    symbols.put("Daug b9 #11", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(7, 0));
    output.add(new Modifier(11, 1));
    symbols.put("Ebb Major7 #11", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(11, 0));
    symbols.put("fmin11", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(6, 0));
    symbols.put("G-6", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(6, 0));
    output.add(new Modifier(9, 2));
    output.add(new Modifier(13, -2));
    symbols.put("Amaj6##9bb13", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(7, 0));
    symbols.put("Ab7", output);

    output = new ArrayList<Modifier>();
    output.add(new Modifier(7, 0));
    symbols.put("A#7", output);

    for (String symbol : symbols.keySet()) {
      List<Modifier> modifiers = Chord.getSymbolModifiers(symbol);

      List<Modifier> expected = symbols.get(symbol);
      List<Modifier> actual = modifiers;

      assertEquals(String.format("A valid symbol should return the chord's modifiers: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetNotes_triads() {
    Map<String, List<Note>> symbols = new HashMap<String, List<Note>>();
    List<Note> output;

    output = new ArrayList<Note>();
    output.add(Note.create("C"));
    output.add(Note.create("E"));
    output.add(Note.create("G"));
    symbols.put("CMajor", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Bb"));
    output.add(Note.create("D"));
    output.add(Note.create("F"));
    symbols.put("Bb", output);

    output = new ArrayList<Note>();
    output.add(Note.create("A#"));
    output.add(Note.create("C##"));
    output.add(Note.create("E#"));
    symbols.put("A#", output);

    output = new ArrayList<Note>();
    output.add(Note.create("D"));
    output.add(Note.create("F"));
    output.add(Note.create("A"));
    symbols.put("Dmin", output);

    output = new ArrayList<Note>();
    output.add(Note.create("E#"));
    output.add(Note.create("G#"));
    output.add(Note.create("B#"));
    symbols.put("e#", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Cb"));
    output.add(Note.create("Ebb"));
    output.add(Note.create("Gb"));
    symbols.put("Cb-", output);

    output = new ArrayList<Note>();
    output.add(Note.create("A"));
    output.add(Note.create("C"));
    output.add(Note.create("Eb"));
    symbols.put("Adiminished", output);

    output = new ArrayList<Note>();
    output.add(Note.create("G#"));
    output.add(Note.create("B"));
    output.add(Note.create("D"));
    symbols.put("g# dim", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Fbbb"));
    output.add(Note.create("Abbbb"));
    output.add(Note.create("Cbbbb"));
    symbols.put("FbbbDIM", output);

    output = new ArrayList<Note>();
    output.add(Note.create("B"));
    output.add(Note.create("D#"));
    output.add(Note.create("F##"));
    symbols.put("B augmented", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Eb"));
    output.add(Note.create("G"));
    output.add(Note.create("B"));
    symbols.put("Eb+", output);

    output = new ArrayList<Note>();
    output.add(Note.create("F#"));
    output.add(Note.create("A#"));
    output.add(Note.create("C##"));
    symbols.put("F# AUG", output);

    for (String symbol : symbols.keySet()) {
      List<Note> notes = Chord.create(symbol).getNotes();

      List<Note> expected = symbols.get(symbol);
      List<Note> actual = notes;

      assertEquals(String.format("A valid triad-only symbol should return the correct chord tones: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetNotes() {
    Map<String, List<Note>> symbols = new HashMap<String, List<Note>>();
    List<Note> output;

    output = new ArrayList<Note>();
    output.add(Note.create("A"));
    output.add(Note.create("C#"));
    output.add(Note.create("E"));
    symbols.put("Amaj", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Bb"));
    output.add(Note.create("Db"));
    output.add(Note.create("F"));
    output.add(Note.create("Ab"));
    symbols.put("Bbmin7", output);

    output = new ArrayList<Note>();
    output.add(Note.create("C"));
    output.add(Note.create("Eb"));
    output.add(Note.create("Gb"));
    output.add(Note.create("Bb"));
    symbols.put("C-7b5", output);

    output = new ArrayList<Note>();
    output.add(Note.create("D"));
    output.add(Note.create("F#"));
    output.add(Note.create("A#"));
    output.add(Note.create("Eb"));
    output.add(Note.create("G#"));
    symbols.put("Daug b9 #11", output);

    output = new ArrayList<Note>();
    output.add(Note.create("Ebb"));
    output.add(Note.create("Gb"));
    output.add(Note.create("Bbb"));
    output.add(Note.create("Db"));
    output.add(Note.create("Ab"));
    symbols.put("Ebb Major7 #11", output);

    output = new ArrayList<Note>();
    output.add(Note.create("F"));
    output.add(Note.create("Ab"));
    output.add(Note.create("C"));
    output.add(Note.create("Bb"));
    symbols.put("fmin11", output);

    output = new ArrayList<Note>();
    output.add(Note.create("G"));
    output.add(Note.create("Bb"));
    output.add(Note.create("D"));
    output.add(Note.create("E"));
    symbols.put("G-6", output);

    output = new ArrayList<Note>();
    output.add(Note.create("A"));
    output.add(Note.create("C#"));
    output.add(Note.create("E"));
    output.add(Note.create("F#"));
    output.add(Note.create("B##"));
    output.add(Note.create("Fb"));
    symbols.put("Amaj6##9bb13", output);

    output = new ArrayList<Note>();
    output.add(Note.create("C"));
    output.add(Note.create("E"));
    output.add(Note.create("Gb"));
    output.add(Note.create("Bb"));
    symbols.put("Cmajb7b5", output);

    for (String symbol : symbols.keySet()) {
      List<Note> notes = Chord.create(symbol).getNotes();

      List<Note> expected = symbols.get(symbol);
      List<Note> actual = notes;

      assertEquals(String.format("A valid symbol should return the correct chord tones: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void test_ChordDominant7() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("C#"));
    expected.add(Note.create("E#"));
    expected.add(Note.create("G#"));
    expected.add(Note.create("B"));

    Set<String> notations = new HashSet<String>();
    notations.add("dom");
    notations.add("dom7");
    notations.add("dominant");
    notations.add("dominant7");
    notations.add("7");

    for (String notation : notations) {
      List<Note> actual = Chord.create(String.format("C#%s", notation)).getNotes();

      String msg = String.format("A %s chord should return the correct chord tones", notation);
      assertEquals(msg, expected, actual);
    }
  }

  @Test
  public void test_ChordSuspended() {
    List<Note> actual = Chord.create("C#sus").getNotes();
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("C#"));
    expected.add(Note.create("F#"));
    expected.add(Note.create("G#"));

    String msg = "A sus chord should be implied as a sus4 and return the correct chord tones";
    assertEquals(msg, expected, actual);
  }

  @Test
  public void test_ChordSuspended4() {
    List<Note> actual = Chord.create("C#sus4").getNotes();
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("C#"));
    expected.add(Note.create("F#"));
    expected.add(Note.create("G#"));

    String msg = "A sus4 chord should return the correct chord tones";
    assertEquals(msg, expected, actual);
  }

  @Test
  public void test_ChordSuspended2() {
    List<Note> actual = Chord.create("C#sus2").getNotes();
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("C#"));
    expected.add(Note.create("D#"));
    expected.add(Note.create("G#"));

    String msg = "A sus2 chord should return the correct chord tones";
    assertEquals(msg, expected, actual);
  }
}
