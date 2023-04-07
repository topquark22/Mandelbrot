package gtf.mandel.configuration;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;

/**
 * Resolve a local copy of a DTD from the classpath.
 *
 *@author   gtf
 */
public final class ClasspathEntityResolver implements LocalEntityResolver {

  private final Map<String, String> entityMap;

  public ClasspathEntityResolver() {
    entityMap = new HashMap<String, String>();
  }

  public void addMapping(String systemId, String localPath) {
    entityMap.put(systemId, localPath);
  }

  /**
   * Return a local copy of the DTD if system ID matches.
   *
   * TODO - how is the publicId used?
   *
   *@param publicId  the public ID from the DOCTYPE declaration
   *@param systemId  the system ID from the DOCTYPE declaration
   *@return          A local copy of the DTD if it matches, or null otherwise
   */
  public InputSource resolveEntity(String publicId, String systemId) {
    InputSource dtd = null;
    String localPath = (String) entityMap.get(systemId);
    if (localPath != null) {
      InputStream in = getClass().getClassLoader().getResourceAsStream(localPath);
      assert in != null : "Failure to get package resource: " + localPath;
      dtd = new InputSource(in);
    }
    return dtd;
  }
}
