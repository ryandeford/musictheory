package chordbuddy;

/**
 *
 * @author Ryan
 */
public class Utilities {

  public static String removeWhitespace(String original) {
    String cleansed = new String();

    if (original != null) {
      cleansed = original.replaceAll("\\s+", "");
    }

    return cleansed;
  }
}
