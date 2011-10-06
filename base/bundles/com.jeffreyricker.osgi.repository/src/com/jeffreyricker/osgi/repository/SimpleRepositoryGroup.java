package com.jeffreyricker.osgi.repository;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

/**
 * 
 * @author Ricker
 * @date Apr 28, 2011
 *
 */
public class SimpleRepositoryGroup extends SimpleRepository implements RepositoryGroup {

	private Set<Repository> repositories;
	private long lastModified;
	
	public SimpleRepositoryGroup(String name, URL url) {
		super(name, url);
		repositories = new HashSet<Repository>();
		lastModified = System.currentTimeMillis();
	}

	public SimpleRepositoryGroup(URL url) {
		super(url);
		repositories = new HashSet<Repository>();
		lastModified = System.currentTimeMillis();
	}

	@Override
	public void addRepository(Repository repository) {
		repositories.add(repository);
		lastModified = System.currentTimeMillis();
	}

	@Override
	public void removeRepository(Repository repository) {
		repositories.remove(repository);
		lastModified = System.currentTimeMillis();
	}

	@Override
	public Set<Repository> getRepositories() {
		return new HashSet<Repository>(repositories);
	}
	
	@Override
	public Resource[] getResources() {
		HashSet<Resource> resources = new HashSet<Resource>();
		for (Repository repository : repositories) {
			Collections.addAll(resources, repository.getResources());
		}
		return resources.toArray(new Resource[] {});
	} 
	
	@Override
	public long getLastModified() {
		return lastModified;
	}

}
