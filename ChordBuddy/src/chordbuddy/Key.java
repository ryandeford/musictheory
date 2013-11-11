package chordbuddy;

/**
 *
 * @author Ryan
 */
public class Key {

  public final MusicTheory.KeyType Type;
  public final int AccidentalCount;

  public Key(MusicTheory.KeyType type, int accidentalCount) {
    this.Type = type;
    this.AccidentalCount = accidentalCount;
  }
}
