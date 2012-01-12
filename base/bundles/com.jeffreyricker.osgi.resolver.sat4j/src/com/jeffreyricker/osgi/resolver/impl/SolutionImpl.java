/**
 * 
 */
package com.jeffreyricker.osgi.resolver.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.resolver.Solution;
import com.jeffreyricker.osgi.resolver.SolutionState;

/**
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class SolutionImpl implements Solution {

	private SolutionState state;

	private Resource resource;

	private Repository repository;

	private Set<Resource> dependencies;

	private List<String> reasons;

	public SolutionImpl(Repository repository, Resource resource) {
		this.repository = repository;
		this.resource = resource;
		state = SolutionState.Unresolved;
		dependencies = new HashSet<Resource>();
		reasons = new ArrayList<String>();
	}

	@Override
	public SolutionState getState() {
		return state;
	}

	public void setState(SolutionState state) {
		this.state = state;
	}

	@Override
	public Resource getResource() {
		return resource;
	}

	@Override
	public Repository getRepository() {
		return repository;
	}

	@Override
	public Set<Resource> getDependencies() {
		return dependencies;
	}

	@Override
	public List<String> getReasons() {
		return reasons;
	}

	public void addReason(String r) {
		reasons.add(r);
	}

	public void addDependency(Resource r) {
		if (r != resource) {
			dependencies.add(r);
		}
	}
}
