package com.jeffreyricker.osgi.builder.source;

import java.io.File;
import java.util.Set;

/**
 * 
 * @author Ricker
 * @date May 2, 2011
 */
public interface BundleSourceFactory {
	
	public Set<BundleSource> createSource(File file);

}
