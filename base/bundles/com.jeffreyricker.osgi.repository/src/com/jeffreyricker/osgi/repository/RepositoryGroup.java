package com.jeffreyricker.osgi.repository;

import java.util.Set;

import org.osgi.service.obr.Repository;

/**
 * Treat a set of repositories as a single repository.
 * 
 * @author Ricker
 * @date Apr 28, 2011
 *
 */
public interface RepositoryGroup extends Repository {
	
	public Set<Repository> getRepositories();
	
	public void addRepository(Repository repository);
	
	public void removeRepository(Repository repository);

}
