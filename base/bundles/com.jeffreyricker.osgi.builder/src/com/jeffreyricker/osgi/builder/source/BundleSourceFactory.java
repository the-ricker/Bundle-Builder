package com.jeffreyricker.osgi.builder.source;

import java.io.File;
import java.util.Set;

/**
 * Creates a bundle source from a given directory. Factories are registered as
 * services.
 * <p>
 * TODO consider changing from File to URI
 * 
 * @author Ricker
 * @date May 2, 2011
 */
public interface BundleSourceFactory {

	/**
	 * @param file
	 * @return
	 */
	public Set<BundleSource> createSource(File file);

}
