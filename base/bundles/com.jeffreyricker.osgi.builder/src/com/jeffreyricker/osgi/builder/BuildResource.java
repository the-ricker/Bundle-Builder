/**
 * 
 */
package com.jeffreyricker.osgi.builder;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * @author Ricker
 * 
 */
public interface BuildResource extends Resource {

	
	public ResourceState getState();

	public void setState(ResourceState state);

	public BundleSource getSource();

	public Set<BuildResource> getParents();

	public Set<BuildResource> getChildren();

	public List<Diagnostic<? extends JavaFileObject>> getCompilerResults();

	public void setCompilerResults(List<Diagnostic<? extends JavaFileObject>> results);

	public List<String> getResolverResults();

	public void setResolverResults(List<String> results);

	public File getFile();

	public void setFile(File jar);

	/**
	 * True if all the dependencies that are build resources are built. This is
	 * not a state of this resource because it is dependent upon the state of
	 * other resources.
	 * 
	 * @return
	 */
	public boolean isReady();

}
