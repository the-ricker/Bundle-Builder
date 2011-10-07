/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.io.File;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.packager.PackagerJob;

/**
 * Wraps the {@link PackagerJob} to do the work and set the state.
 * 
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class PackageStep implements BuildStep {

	private BuildResource resource;
	private PackagerJob packager;

	public PackageStep(BuildResource resource, PackagerJob packager) {
		this.resource = resource;
		this.packager = packager;
	}

	@Override
	public BuildResource call() throws Exception {
		resource.setState(BuildResource.State.Packaging);
		try {
			File jar = packager.call();
			resource.setFile(jar);
			resource.setState(BuildResource.State.Built);
		} catch (Exception e) {
			e.printStackTrace();
			resource.setState(BuildResource.State.FailedPackaging);
		}
		return resource;
	}

}
