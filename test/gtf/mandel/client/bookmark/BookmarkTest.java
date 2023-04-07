package gtf.mandel.client.bookmark;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gtf.mandel.model.Region;
import gtf.math.Complex;


/**
 * Test the equals() method of the Bookmark value object.
 *
 *@author   gtf
 */
public class BookmarkTest {

  private Bookmark b;

  @Before
  public void setUp() {
    b = someBookmark();
  }

  private static Bookmark someBookmark() {
    Bookmark sb = new Bookmark();
    sb.setLimit(100);
    sb.setDescription("Foo");
    sb.setRegion(new Region(-2.0, -2.0, 4.0));
    sb.setFormulaName("my.formula");
    return sb;
  }

  @Test
  public void testEqualsNonJulia() {
    assertEquals(b, b);
  }

  @Test
  public void testEqualsJulia() {
    b.setJuliaValue(new Complex(6.6, 4.314159));
    assertEquals(b, b);
  }

  @Test
  public void testJuliaVsNonJulia() {
    Bookmark b2 = someBookmark();
    b2.setJuliaValue(new Complex(6.6, 4.314159));
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testJuliaValuesDiffer() {
    b.setJuliaValue(new Complex(6.6, 4.314159));
    Bookmark b2 = someBookmark();
    b2.setJuliaValue(new Complex(6.623, 4.314159));
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testLimitsDiffer() {
    Bookmark b2 = someBookmark();
    b2.setLimit(b.getLimit() + 1);
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testDescriptionsDiffer() {
    Bookmark b2 = someBookmark();
    b2.setDescription(b.getDescription() + "Bar");
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testDescriptionsDifferByNull() {
    Bookmark b2 = someBookmark();
    b2.setDescription(null);
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testFormulaNamesDiffer() {
    Bookmark b2 = someBookmark();
    b2.setFormulaName(b.getFormulaName() + "Bar");
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testFormulaNamesDifferByNull() {
    Bookmark b2 = someBookmark();
    b2.setFormulaName(null);
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testRegionsDiffer() {
    Bookmark b2 = someBookmark();
    b2.setRegion(new Region(-44.33, 643.234, 1.0));
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }

  @Test
  public void testRegionsDifferByNull() {
    Bookmark b2 = someBookmark();
    assertFalse(b.equals(b2));
    assertFalse(b2.equals(b));
  }
}

