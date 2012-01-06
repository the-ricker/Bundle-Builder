/**
 * 
 */
package com.jeffreyricker.osgi.builder.source.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.Manifest;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class SimpleBundleSource implements BundleSource {

	private List<File> sources;
	private File output;
	private Manifest manifest;

	private Set<Resource> dependencies;
	private Map<String, File> binIncludes;

	
	@Override
	public Set<Resource> getDependencies() {
		return dependencies;
	}

	@Override
	public void setDependencies(Set<Resource> dependencies) {
		this.dependencies = dependencies;
	}

	@Override
	public Manifest getManifest() throws IOException {
		return manifest;
	}

	@Override
	public List<File> getSources() {
		return sources;
	}

	@Override
	public File getOutput() {
		return output;
	}

	@Override
	public Map<String, File> getBinIncludes() {
		return binIncludes;
	}

	public void setSources(List<File> sources) {
		this.sources = sources;
	}

	public void setOutput(File output) {
		this.output = output;
	}

	public void setManifest(Manifest manifest) {
		this.manifest = manifest;
	}

	public void setBinIncludes(Map<String, File> binIncludes) {
		this.binIncludes = binIncludes;
	}

	
	
}
