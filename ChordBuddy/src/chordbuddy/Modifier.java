package chordbuddy;

/**
 *
 * @author Ryan
 */
public class Modifier {

  public final int TargetIndex;
  public final int Quality;

  public Modifier(int targetIndex, int quality) {
    this.TargetIndex = targetIndex;
    this.Quality = quality;
  }

  @Override
  public boolean equals(Object other) {
    if (other == null) {
      return false;
    } else if (other == this) {
      return true;
    } else if (!(other instanceof Modifier)) {
      return false;
    }

    Modifier otherModifier = (Modifier) other;
    return this.TargetIndex == otherModifier.TargetIndex &&
            this.Quality == otherModifier.Quality;
  }

  @Override
  public String toString() {
    return String.format("(%d, %d)", this.TargetIndex, this.Quality);
  }
}
