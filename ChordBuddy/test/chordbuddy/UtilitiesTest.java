package chordbuddy;

import java.util.HashMap;
import java.util.Map;
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
public class UtilitiesTest {

  public UtilitiesTest() {
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
  public void testRemoveWhitespace_null() {
    String expected = "";
    String actual = Utilities.removeWhitespace(null);

    assertEquals("A null string should return an empty string", expected, actual);
  }

  @Test
  public void testRemoveWhitespace_empty() {
    String expected = "";
    String actual = Utilities.removeWhitespace("");

    assertEquals("An empty string should return a new, empty string", expected, actual);
  }

  @Test
  public void testRemoveWhitespace() {
    Map<String, String> strings = new HashMap<String, String>();
    strings.put(" ", "");
    strings.put("\t", "");
    strings.put("a b c", "abc");
    strings.put("some \n chord", "somechord");
    strings.put("\r\n\t  foo  \r\n\t", "foo");

    for (String original : strings.keySet()) {
      String cleansed = Utilities.removeWhitespace(original);

      String expected = strings.get(original);
      String actual = cleansed;

      assertEquals(String.format("A string should be returned with all whitespace characters removed: '%s'", original), expected, actual);
    }
  }
}
