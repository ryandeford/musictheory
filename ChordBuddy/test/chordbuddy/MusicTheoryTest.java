package chordbuddy;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ryan
 */
public class MusicTheoryTest {

  @Test
  public void testGetMajorScale_null() {
    List<Note> scale = MusicTheory.getMajorScale(null);
    assertTrue("A null tonic will yield a null major scale", scale == null);
  }

  @Test
  public void testGetMajorScale_baseNatural() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("C"));
    expected.add(Note.create("D"));
    expected.add(Note.create("E"));
    expected.add(Note.create("F"));
    expected.add(Note.create("G"));
    expected.add(Note.create("A"));
    expected.add(Note.create("B"));

    List<Note> actual = MusicTheory.getMajorScale(Note.create("C"));
    assertEquals("A major scale can be created using a base tonic natural key", expected, actual);
  }

  @Test
  public void testGetMajorScale_baseFlat() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("F"));
    expected.add(Note.create("G"));
    expected.add(Note.create("A"));
    expected.add(Note.create("Bb"));
    expected.add(Note.create("C"));
    expected.add(Note.create("D"));
    expected.add(Note.create("E"));

    List<Note> actual = MusicTheory.getMajorScale(Note.create("F"));
    assertEquals("A major scale can be created using a base tonic flat key", expected, actual);
  }

  @Test
  public void testGetMajorScale_baseSharp() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("E"));
    expected.add(Note.create("F#"));
    expected.add(Note.create("G#"));
    expected.add(Note.create("A"));
    expected.add(Note.create("B"));
    expected.add(Note.create("C#"));
    expected.add(Note.create("D#"));

    List<Note> actual = MusicTheory.getMajorScale(Note.create("E"));
    assertEquals("A major scale can be created using a base tonic sharp key", expected, actual);
  }

  @Test
  public void testGetMajorScale_alteredFlat() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("Fb"));
    expected.add(Note.create("Gb"));
    expected.add(Note.create("Ab"));
    expected.add(Note.create("Bbb"));
    expected.add(Note.create("Cb"));
    expected.add(Note.create("Db"));
    expected.add(Note.create("Eb"));

    List<Note> actual = MusicTheory.getMajorScale(Note.create("Fb"));
    assertEquals("A major scale can be created using an altered tonic flat key", expected, actual);
  }

  @Test
  public void testGetMajorScale_alteredSharp() {
    List<Note> expected = new ArrayList<Note>();
    expected.add(Note.create("D##"));
    expected.add(Note.create("E##"));
    expected.add(Note.create("F###"));
    expected.add(Note.create("G##"));
    expected.add(Note.create("A##"));
    expected.add(Note.create("B##"));
    expected.add(Note.create("C###"));

    List<Note> actual = MusicTheory.getMajorScale(Note.create("D##"));
    assertEquals("A major scale can be created using an altered tonic sharp key", expected, actual);
  }

  @Test
  public void testGetScaleSkeleton_null() {
    List<MusicTheory.NoteName> skeleton = MusicTheory.getScaleSkeleton(null);
    assertTrue("A null tonic will yield a null scale skeleton", skeleton == null);
  }

  @Test
  public void testGetScaleSkeleton() {
    List<MusicTheory.NoteName> expected = new ArrayList<MusicTheory.NoteName>();
    expected.add(MusicTheory.NoteName.C);
    expected.add(MusicTheory.NoteName.D);
    expected.add(MusicTheory.NoteName.E);
    expected.add(MusicTheory.NoteName.F);
    expected.add(MusicTheory.NoteName.G);
    expected.add(MusicTheory.NoteName.A);
    expected.add(MusicTheory.NoteName.B);

    List<MusicTheory.NoteName> actual = MusicTheory.getScaleSkeleton(Note.create("C##"));
    assertEquals("A skeleton scale can be created using a base tonic", expected, actual);
  }
}
