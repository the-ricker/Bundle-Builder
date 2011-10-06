package com.jeffreyricker.osgi.builder.source;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;

import org.osgi.service.obr.Resource;

/**
 * The bundle source is the handle used to compile an OSGi bundle. 
 * 
 * @author Ricker
 * 
 */
public interface BundleSource {

	/**
	 * The resolved dependencies used to create the compilation class path.
	 * 
	 * @return
	 */
	public Set<Resource> getDependencies();
	
	public void setDependencies(Set<Resource> resources);

	/**
	 * The bundle manifest
	 * 
	 * @return
	 */
	public Manifest getManifest() throws IOException;

	/**
	 * The directories that contain the Java source files
	 * 
	 * @return
	 */
	public List<File> getSources();

	/**
	 * The directory to place the compiled Java classes
	 * 
	 * @return
	 */
	public File getOutput();
	
	/**
	 * Compiled files that should be included in the class path. These files
	 * must exist in the packaged bundle after the compilation.
	 * 
	 * @return map of relative path name in the jar to the source file
	 */
	public Map<String, File> getBinIncludes();

}
