package com.jeffreyricker.osgi.builder.source;

import java.io.File;
import java.util.Set;

/**
 * Creates a bundle source from a given directory. Factories are registered as
 * services.
 * 
 * @author Ricker
 * @date May 2, 2011
 */
public interface BundleSourceFactory {

	public Set<BundleSource> createSource(File file);

}
