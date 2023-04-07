package gtf.mandel.client.bookmark;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import gtf.mandel.configuration.ClasspathEntityResolver;
import gtf.mandel.configuration.LocalEntityResolver;


/**
 * Parse the bookmarks.xml.
 *
 * TODO: Except for the rules and String constants, this is almost the same
 * as PluginDescriptorParser. Think about how to refactor it by encapsulating
 * rule-setting code somehow.
 *
 *@author   gtf
 */
class BookmarksParser {

  /**
   * The Public ID for a Mandelbrot plugin descriptor.
   */
  final static String PUBLIC_ID = "-//Geoffrey Trueman Falk//DTD Mandelbrot Bookmarks 1.0//EN";

  /**
   * The System ID for a Mandelbrot plugin descriptor.
   */
  final static String SYSTEM_ID = "http://gtf.cirp.org/DTD/mandelbrot-bookmarks-1_0.dtd";

  /**
   * The file location for the DTD.
   */
  final static String DTD_LOCATION = "META-INF/mandelbrot-bookmarks-1_0.dtd";

  private Bookmarks bookmarks;

  private final Digester digester;

  BookmarksParser() {
    LocalEntityResolver resolver = new ClasspathEntityResolver();
    resolver.addMapping(SYSTEM_ID, DTD_LOCATION);
    digester = new Digester();
    digester.setEntityResolver(resolver);
    setRules();
  }

  private void setRules() {
    digester.addObjectCreate("bookmarks", "gtf.mandel.client.bookmark.Bookmarks");
    digester.addObjectCreate("bookmarks", "gtf.mandel.client.bookmark.Folder");
    digester.addSetNext("bookmarks", "setFolder", "gtf.mandel.client.bookmark.Folder");
    digester.addObjectCreate("*/folder", "gtf.mandel.client.bookmark.Folder");
    digester.addSetNext("*/folder", "addFolder", "gtf.mandel.client.bookmark.Folder");
    digester.addSetProperties("*/folder", new String[] { "title" }, new String[] { "title" });
    digester.addObjectCreate("*/bookmark", "gtf.mandel.client.bookmark.Bookmark");
    digester.addSetNext("*/bookmark", "addBookmark", "gtf.mandel.client.bookmark.Bookmark");
    digester.addBeanPropertySetter("*/bookmark/name");
    digester.addBeanPropertySetter("*/bookmark/description");
    digester.addBeanPropertySetter("*/bookmark/formula", "formulaName");
    digester.addBeanPropertySetter("*/bookmark/limit");
    digester.addObjectCreate("*/bookmark/mandelbrot", "gtf.mandel.model.mutable.RegionBean");
    digester.addSetNext("*/bookmark/mandelbrot", "setRegion", "gtf.mandel.model.mutable.RegionBean");
    digester.addBeanPropertySetter("*/bookmark/mandelbrot/x");
    digester.addBeanPropertySetter("*/bookmark/mandelbrot/y");
    digester.addBeanPropertySetter("*/bookmark/mandelbrot/span");
    digester.addObjectCreate("*/bookmark/julia", "gtf.mandel.model.mutable.JuliaRegionBean");
    digester.addSetNext("*/bookmark/julia", "setRegion", "gtf.mandel.model.mutable.RegionBean");
    digester.addSetProperties("*/bookmark/julia", new String[] { "x", "y" }, new String[] { "juliaX", "juliaY" });
    digester.addBeanPropertySetter("*/bookmark/julia/x");
    digester.addBeanPropertySetter("*/bookmark/julia/y");
    digester.addBeanPropertySetter("*/bookmark/julia/span");
  }

  Bookmarks parse(InputStream in) throws BookmarksException {
    try {
      bookmarks = (Bookmarks) digester.parse(in);
    } catch (IOException eieio) {
      throw new BookmarksException("Could not read bookmarks file");
    } catch (SAXException sex) {
      sex.printStackTrace();
      throw new BookmarksException("Error parsing bookmarks file");
    }
    bookmarks.validate();
    return bookmarks;
  }

  public Bookmarks getBookmarks() {
    return bookmarks;
  }
}

