package chordbuddy;

import java.util.HashMap;
import java.util.HashSet;
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
public class NoteTest {

  public NoteTest() {
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
  public void testCreate_invalid() {
    Note expected = null;
    Note actual = Note.create("badnote");
    assertEquals("Creating a note instance with an invalid symbol should return null", expected, actual);
  }

  @Test
  public void testIsValidSymbol_null() {
    assertTrue("A null string is not a valid note symbol", !Note.isValidSymbol(null));
  }

  @Test
  public void testIsValidSymbol_empty() {
    assertTrue("An empty string is not a valid note symbol", !Note.isValidSymbol(""));
  }

  @Test
  public void testIsValidSymbol() {
    Set<String> symbolsValid = new HashSet<String>();
    symbolsValid.add("A");
    symbolsValid.add("Bb");
    symbolsValid.add("C#");
    symbolsValid.add("Dbb");
    symbolsValid.add("E##");
    symbolsValid.add("F #");
    symbolsValid.add("g");

    for (String symbol : symbolsValid) {
      assertTrue(String.format("The following note symbol should be valid: '%s'", symbol), Note.isValidSymbol(symbol));
    }

    Set<String> symbolsInvalid = new HashSet<String>();
    symbolsInvalid.add("AA");
    symbolsInvalid.add("B-");
    symbolsInvalid.add("CB");
    symbolsInvalid.add("Dd");
    symbolsInvalid.add("E4");
    symbolsInvalid.add("fx");
    symbolsInvalid.add("X#");

    for (String symbol : symbolsInvalid) {
      assertTrue(String.format("The following note symbol should be invalid: '%s'", symbol), !Note.isValidSymbol(symbol));
    }
  }

  @Test
  public void testGetSymbolLetter_null() {
    String expected = null;
    MusicTheory.NoteName actual = Note.getSymbolLetter(null);

    assertEquals("A null symbol should return a null note letter", expected, actual);
  }

  @Test
  public void testGetSymbolLetter_empty() {
    String expected = null;
    MusicTheory.NoteName actual = Note.getSymbolLetter("");

    assertEquals("An empty symbol should return a null note letter", expected, actual);
  }

  @Test
  public void testGetSymbolLetter_invalid() {
    String expected = null;
    MusicTheory.NoteName actual = Note.getSymbolLetter("Hb");

    assertEquals("An invalid symbol should return a null note letter", expected, actual);
  }

  @Test
  public void testGetSymbolLetter() {
    Map<String, MusicTheory.NoteName> symbols = new HashMap<String, MusicTheory.NoteName>();
    symbols.put("A###", MusicTheory.NoteName.A);
    symbols.put("B b", MusicTheory.NoteName.B);
    symbols.put("C", MusicTheory.NoteName.C);
    symbols.put("dbb", MusicTheory.NoteName.D);
    symbols.put("E b b", MusicTheory.NoteName.E);
    symbols.put("f#", MusicTheory.NoteName.F);
    symbols.put("g", MusicTheory.NoteName.G);

    for (String symbol : symbols.keySet()) {
      MusicTheory.NoteName letter = Note.getSymbolLetter(symbol);

      MusicTheory.NoteName expected = symbols.get(symbol);
      MusicTheory.NoteName actual = letter;

      assertEquals(String.format("A valid symbol should return the note's base letter: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetSymbolQuality_null() {
    String expected = null;
    Integer actual = Note.getSymbolQuality(null);

    assertEquals("A null symbol should return a null note quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_empty() {
    String expected = null;
    Integer actual = Note.getSymbolQuality("");

    assertEquals("An empty symbol should return a null note quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality_invalid() {
    String expected = null;
    Integer actual = Note.getSymbolQuality("Hb");

    assertEquals("An invalid symbol should return a null note quality", expected, actual);
  }

  @Test
  public void testGetSymbolQuality() {
    Map<String, Integer> symbols = new HashMap<String, Integer>();
    symbols.put("A###", 3);
    symbols.put("B b", -1);
    symbols.put("C", 0);
    symbols.put("dbb", -2);
    symbols.put("E b b", -2);
    symbols.put("f#", 1);
    symbols.put("g", 0);
    symbols.put("a##########", 10);
    symbols.put("abbbbbbbbbb", -10);

    for (String symbol : symbols.keySet()) {
      Integer quality = Note.getSymbolQuality(symbol);

      Integer expected = symbols.get(symbol);
      Integer actual = quality;

      assertEquals(String.format("A valid symbol should return the note's quality: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testGetLetter() {
    Note n = Note.create("Dbb");
    MusicTheory.NoteName expected = MusicTheory.NoteName.D;
    MusicTheory.NoteName actual = n.getLetter();
    assertEquals("A note object should have a valid letter property", expected, actual);
  }

  @Test
  public void testGetQuality() {
    Note n = Note.create("D##");
    Integer expected = 2;
    Integer actual = n.getQuality();
    assertEquals("A note object should have a valid quality property", expected, actual);
  }

  @Test
  public void testGetFullName() {
    Map<String, String> symbols = new HashMap<String, String>();
    symbols.put("A###", "A###");
    symbols.put("B b", "Bb");
    symbols.put("C", "C");
    symbols.put("dbb", "Dbb");
    symbols.put("E b b", "Ebb");
    symbols.put("f#", "F#");
    symbols.put("g", "G");

    for (String symbol : symbols.keySet()) {
      Note n = Note.create(symbol);

      String expected = symbols.get(symbol);
      String actual = n.getFullName();

      assertEquals(String.format("A valid note returns a correctly normalized full name: '%s'", symbol), expected, actual);
    }
  }

  @Test
  public void testFlatify_0() {
    Note n = Note.create("C");

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.flatify(0);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Flatting a note 0 times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Flatting a note 0 times should yield the same quality", qualityOriginal, qualityAfter);
  }

  @Test
  public void testFlatify_positive() {
    Note n = Note.create("C");
    Integer offset = 2;

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.flatify(offset);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Flatting a note n times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Flatting a note n times should yield a quality of n less", (int) (qualityOriginal - offset), (int) qualityAfter);
  }

  @Test
  public void testFlatify_negative() {
    Note n = Note.create("C");
    Integer offset = -2;

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.flatify(offset);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Flatting a note n times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Flatting a note -n times should yield a quality of n less", (int) (qualityOriginal + offset), (int) qualityAfter);
  }

  @Test
  public void testSharpify_0() {
    Note n = Note.create("C");

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.sharpify(0);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Sharping a note 0 times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Sharping a note 0 times should yield the same quality", qualityOriginal, qualityAfter);
  }

  @Test
  public void testSharpify_positive() {
    Note n = Note.create("C");
    Integer offset = 2;

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.sharpify(offset);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Sharping a note n times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Sharping a note n times should yield a quality of n more", (int) (qualityOriginal + offset), (int) qualityAfter);
  }

  @Test
  public void testSharpify_negative() {
    Note n = Note.create("C");
    Integer offset = -2;

    MusicTheory.NoteName letterOriginal = n.getLetter();
    Integer qualityOriginal = n.getQuality();

    n.sharpify(offset);

    MusicTheory.NoteName letterAfter = n.getLetter();
    Integer qualityAfter = n.getQuality();

    assertEquals("Sharping a note n times should yield the same letter", letterOriginal, letterAfter);
    assertEquals("Sharping a note -n times should yield a quality of n more", (int) (qualityOriginal - offset), (int) qualityAfter);
  }

  @Test
  public void testEquals() {
    String symbol = "Cbb";
    Note a = Note.create(symbol);
    Note b = Note.create(symbol);

    assertTrue("Equal note objects should claim equality", a.equals(b));

    b.sharpify(1);

    assertTrue("Unequal note objects should claim inequality", !a.equals(b));
  }

  @Test
  public void testToString() {
    testGetFullName();
  }
}
