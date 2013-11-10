package chordbuddy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

  public ChordTest() {
  }

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
    qualities.add("maj");
    qualities.add("major");
    qualities.add("min");
    qualities.add("minor");
    qualities.add("-");
    qualities.add("dim");
    qualities.add("diminished");
    qualities.add("aug");
    qualities.add("augmented");
    qualities.add("+");

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

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
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
    String expected = null;
    String actual = Chord.getSymbolTonic(null);

    assertEquals("A null symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic_empty() {
    String expected = null;
    String actual = Chord.getSymbolTonic("");

    assertEquals("An empty symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic_invalid() {
    String expected = null;
    String actual = Chord.getSymbolTonic("badchord");

    assertEquals("An invalid symbol should return a null tonic", expected, actual);
  }

  @Test
  public void testGetSymbolTonic() {
    Map<String, String> symbols = new HashMap<String, String>();
    symbols.put("Amaj", "A");
    symbols.put("B bmin", "Bb");
    symbols.put("C", "C");
    symbols.put("D###", "D###");
    symbols.put("E b b Major7 #11", "Ebb");
    symbols.put("fmin11", "f");
    symbols.put("G-6", "G");

    for (String symbol : symbols.keySet()) {
      String tonic = Chord.getSymbolTonic(symbol);

      String expected = symbols.get(symbol);
      String actual = tonic;

      assertEquals(String.format("A valid symbol should return the chord's tonic: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetSymbolQuality_null() {
    String expected = null;
    String actual = Chord.getSymbolQuality(null);

    assertEquals("A null symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_empty() {
    String expected = null;
    String actual = Chord.getSymbolQuality("");

    assertEquals("An empty symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_invalid() {
    String expected = null;
    String actual = Chord.getSymbolQuality("badchord");

    assertEquals("An invalid symbol should return a null quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality() {
    Map<String, String> symbols = new HashMap<String, String>();
    symbols.put("Amaj", "maj");
    symbols.put("Bbmin7", "min");
    symbols.put("Cdim7b5", "dim");
    symbols.put("Daugb9#11", "aug");
    symbols.put("Ebb Ma j or7 #11", "Major");
    symbols.put("fmin11", "min");
    symbols.put("G-6", "-");
    symbols.put("a+7#11", "+");
    symbols.put("B diminished", "diminished");
    symbols.put("C", null);

    for (String symbol : symbols.keySet()) {
      String quality = Chord.getSymbolQuality(symbol);

      String expected = symbols.get(symbol);
      String actual = quality;

      assertEquals(String.format("A valid symbol should return the chord's quality: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetSymbolModifiers_null() {
    String expected = null;
    String actual = Chord.getSymbolModifiers(null);

    assertEquals("A null symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers_empty() {
    String expected = null;
    String actual = Chord.getSymbolModifiers("");

    assertEquals("An empty symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers_invalid() {
    String expected = null;
    String actual = Chord.getSymbolModifiers("badchord");

    assertEquals("An invalid symbol should return null modifiers", expected, actual);
  }

  @Test
  public void testGetSymbolModifiers() {
    Map<String, String> symbols = new HashMap<String, String>();
    symbols.put("Amaj", null);
    symbols.put("Bbmin7", "7");
    symbols.put("Cdim7b5", "7b5");
    symbols.put("Daug b9 #11", "b9#11");
    symbols.put("Ebb Major7 #11", "7#11");
    symbols.put("fmin11", "11");
    symbols.put("G-6", "6");

    for (String symbol : symbols.keySet()) {
      String modifiers = Chord.getSymbolModifiers(symbol);

      String expected = symbols.get(symbol);
      String actual = modifiers;

      assertEquals(String.format("A valid symbol should return the chord's modifiers: '%s'", symbol), expected, actual);
    }
  }
}
