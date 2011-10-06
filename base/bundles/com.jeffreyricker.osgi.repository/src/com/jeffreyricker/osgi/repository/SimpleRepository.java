/**
 * 
 */
package com.jeffreyricker.osgi.repository;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

/**
 * @author Ricker
 * 
 */
public class SimpleRepository implements Repository {

	private URL url;
	private String name;
	private Set<Resource> resources;
	private long lastModified;

	public SimpleRepository(URL url) {
		this(url.toString(), url);
	}
	
	public SimpleRepository(String name, URL url) {
		this.url = url;
		this.name = name;
		lastModified = 0;
		resources = new HashSet<Resource>();
	}

	public String getURI() throws URISyntaxException {
		return url.toURI().toString();
	}

	@Override
	public Resource[] getResources() {
		return resources.toArray(new Resource[] {});
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public long getLastModified() {
		return lastModified;
	}

	public void clear() {
		resources.clear();
		lastModified = System.currentTimeMillis();
	}

	public void addResource(Resource resource) {
		resources.add(resource);
		lastModified = System.currentTimeMillis();
	}
	
	public void removeResource(Resource resource) {
		resources.remove(resource);
		lastModified = System.currentTimeMillis();
	}
	
	public void addResources(Collection<Resource> resource) {
		resources.addAll(resource);
		lastModified = System.currentTimeMillis();
	}
	
	public void removeResources(Collection<Resource> resource) {
		resources.removeAll(resource);
		lastModified = System.currentTimeMillis();
	}

	@Override
	public URL getURL() {
		return url;
	}

	
	public boolean isLocal() {
		return false;
	}

}
