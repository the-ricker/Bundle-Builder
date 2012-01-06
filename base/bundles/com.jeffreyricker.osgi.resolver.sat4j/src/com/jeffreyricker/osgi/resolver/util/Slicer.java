/**
 * 
 */
package com.jeffreyricker.osgi.resolver.util;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.obr.Capability;
import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resource;

/**
 * Utility for finding the subset of a repository that we are interested in.
 * 
 * @author Ricker
 * 
 */
public class Slicer {

	public static Set<Resource> slice(Repository repository, Resource project) {
		HashSet<Resource> slice = new HashSet<Resource>();
		for (Resource resource : repository.getResources()) {
			if (match(project, resource)) {
				slice.add(resource);
			}
		}
		return slice;
	}

	public static boolean match(Resource requires, Resource provides) {
		if (requires != null && requires.getRequirements() != null) {
			for (Requirement req : requires.getRequirements()) {
				if (match(req, provides)) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean match(Requirement req, Resource provides) {
		if (req != null && provides != null && provides.getCapabilities() != null) {
			for (Capability cap : provides.getCapabilities()) {
				if (req.isSatisfied(cap)) {
					return true;
				}
			}
		}
		return false;
	}

}
