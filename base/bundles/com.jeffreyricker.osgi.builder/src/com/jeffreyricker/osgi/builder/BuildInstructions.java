package com.jeffreyricker.osgi.builder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.osgi.service.obr.Repository;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * The set of things to build.
 * 
 * @author Ricker
 * @date Apr 27, 2011
 *
 */
public interface BuildInstructions {
	
	public String getVersionQualifier();
	
	public Repository getRepository();
	
	public File getOutputDirectory();
	
	public URL getOutputURL() throws MalformedURLException;
	
	public boolean isParallel();
	
	public Set<BundleSource> getBundleSources();

}
