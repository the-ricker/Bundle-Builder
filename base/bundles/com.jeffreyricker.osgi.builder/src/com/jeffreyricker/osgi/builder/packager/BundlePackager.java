package com.jeffreyricker.osgi.builder.packager;

import java.io.File;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * Service for creating the jar file of a bundle.
 * 
 * @author Ricker
 * @date Apr 27, 2011
 *
 */
public interface BundlePackager {
	
	/**
	 * 
	 * @param source
	 * @param targetDir 
	 * @return a job that will create the JAR file 
	 */
	public PackagerJob createPackager(BundleSource source, File targetDir);

}
