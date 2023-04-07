package gtf.mandel.client.bookmark;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

/**
 * Parse the bookmarks.xml.
 *
 * TODO: Except for the rules and String constants, this is almost the same
 * as PluginDescriptorParser. Think about how to refactor it by encapsulating
 * rule-setting code somehow.
 *
 *@author   gtf
 */
public class BookmarksParserTest {


  private static final String TEST_INPUT_1 = "gtf/mandel/client/bookmark/bookmarks-1.xml";

  private static final int EXPECTED_SIZE_1 = 4;
 
  @Test
  public void testParse1() throws IOException, BookmarksException {
    BookmarksParser parser = new BookmarksParser();
    InputStream in = getClass().getClassLoader().getResourceAsStream(TEST_INPUT_1);
    Bookmarks b = parser.parse(in);
    assertEquals("Wrong size", EXPECTED_SIZE_1, b.getFolder().getSize());
    System.out.println(b.getFolder().toString());
  }
}

