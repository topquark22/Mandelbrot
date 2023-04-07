package gtf.mandel.configuration;

import org.xml.sax.EntityResolver;

/**
 * Resolve a local copy of a DTD from the classpath.
 *
 *@author   gtf
 */
public interface LocalEntityResolver extends EntityResolver {

  void addMapping(String systemId, String localFile);

}
