package com.jeffreyricker.osgi.resolver;

import java.util.List;
import java.util.Set;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

/**
 * The resolution dependencies to a given resource against a given repository. 
 * 
 * The result of the {@link ResolverJob}. 
 * 
 * @author Ricker
 * @date Apr 27, 2011
 *
 */
public interface Solution {
	

	public SolutionState getState();
	
	public Resource getResource();
	
	public Repository getRepository();
	
	public Set<Resource> getDependencies();
	
	public List<String> getReasons();
	
}
