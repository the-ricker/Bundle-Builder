/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.Manifest;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.BuildInstructions;
import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.ResourceState;
import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.manifest.ManifestUtil;
import com.jeffreyricker.osgi.repository.ResourceLoader;
import com.jeffreyricker.osgi.repository.SimpleRepository;

/**
 * @author Ricker
 * @date Apr 29, 2011
 * 
 */
public class BuildRepositoryImpl extends SimpleRepository implements BuildRepository {

	private Set<BuildResource> resources;

	public BuildRepositoryImpl(String name, URL url) {
		super(name, url);
		resources = new HashSet<BuildResource>();
	}
	
	public BuildRepositoryImpl(BuildInstructions instructions) throws IOException {
		super("Source", instructions.getOutputURL());
		resources = new HashSet<BuildResource>();
		populate(instructions);
	}
	

	private void populate(BuildInstructions instructions) throws IOException {
		for (BundleSource source : instructions.getBundleSources()) {
			BuildResourceImpl resource = new BuildResourceImpl(this, source);
			resource.setState(ResourceState.Unresolved);
			Manifest manifest = source.getManifest();
			ManifestUtil.qualifyVersion(manifest, instructions.getVersionQualifier());
			ResourceLoader.populate(resource, manifest);
			addResource(resource);
		}
		
	}

	@Override
	public void addResource(Resource resource) {
		super.addResource(resource);
		if (resource instanceof BuildResource) {
			resources.add((BuildResource) resource);
		}
	}

	@Override
	public Set<BuildResource> getBuildResources() {
		return resources;
	}

	/**
	 * True so long as at least one resource is working.
	 * 
	 * @return
	 */
	@Override
	public boolean isBuilding() {
		for (BuildResource r : resources) {
			if (r.getState().isWorking()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasWork() {
		for (BuildResource r : resources) {
			if (r.getState().hasWork()) {
				return true;
			}
		}
		return false;
	}

}
