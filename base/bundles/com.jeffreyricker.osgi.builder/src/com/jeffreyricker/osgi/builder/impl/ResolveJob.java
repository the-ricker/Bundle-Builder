/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.resolver.ResolverJob;
import com.jeffreyricker.osgi.resolver.Solution;

/**
 * @author Ricker
 * @date Apr 29, 2011
 * 
 */
public class ResolveJob implements BuildJob {

	private BuildResource resource;

	private ResolverJob resolver;

	public ResolveJob(BuildResource resource, ResolverJob resolver) {
		this.resource = resource;
		this.resolver = resolver;
	}

	@Override
	public BuildResource call() throws Exception {
		Solution solution = resolver.call();
		if (solution == null || solution.getState() != Solution.State.Satisfied) {
			resource.setState(BuildResource.State.FailedResolve);
		} else {
			log(solution);
			resource.getSource().setDependencies(solution.getDependencies());
			for (Resource r : solution.getDependencies()) {
				if (r instanceof BuildResource) {
					BuildResource br = (BuildResource) r;
					resource.getParents().add(br);
					br.getChildren().add(resource);
				}
			}
			resource.setState(BuildResource.State.Resolved);
		}
		return resource;
	}

	private void log(Solution solution) {
		System.out.println(solution.getResource().getId() + " solution:");
		for (String reason : solution.getReasons()) {
			System.out.println("  Reason: " + reason);
		}
		for (Resource resource : solution.getDependencies()) {
			System.out.println("  Dependency: " + resource.getId());
		}
	}

}
