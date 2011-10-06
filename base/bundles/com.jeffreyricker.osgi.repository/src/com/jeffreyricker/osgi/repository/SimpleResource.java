/**
 * 
 */
package com.jeffreyricker.osgi.repository;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Version;
import org.osgi.service.obr.Capability;
import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Requirement;
import org.osgi.service.obr.Resource;

/**
 * @author jricker
 * 
 */
public class SimpleResource implements Resource {

	protected Repository repository;

	private URL url;
	private Map<String, Object> properties;
	private Version version;
	private Set<Requirement> requirements;
	private Set<Capability> capabilities;
	private Set<String> categories;
	private long size;

	public SimpleResource(Repository repository, URL url) {
		this.url = url;
		size = 0;
		properties = new HashMap<String, Object>();
		requirements = new HashSet<Requirement>();
		capabilities = new HashSet<Capability>();
		categories = new HashSet<String>();
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	@Override
	public String getSymbolicName() {
		return (String) properties.get(SYMBOLIC_NAME);
	}

	@Override
	public String getPresentationName() {
		return (String) properties.get(PRESENTATION_NAME);
	}

	@Override
	public Version getVersion() {
		return version;
	}
	
	public void setVersion(String v) {
		properties.put(VERSION, v);
		version = new Version(v);
	}

	@Override
	public String getId() {
		return (String) properties.get(ID);
	}

	@Override
	public URL getURL() {
		return url;
	}
	
	
	public String getURI() throws URISyntaxException {
		return url.toURI().toString();
	}

	@Override
	public Requirement[] getRequirements() {
		return requirements.toArray(new Requirement[] {});
	}

	@Override
	public Capability[] getCapabilities() {
		return capabilities.toArray(new Capability[] {});
	}

	@Override
	public String[] getCategories() {
		return categories.toArray(new String[] {});
	}

	@Override
	public Repository getRepository() {
		return repository;
	}

	public Long getSize() {
		return size;
	}

	public boolean isLocal() {
		return false;
	}

	public void put(String id, Object value) {
		properties.put(id, value);
	}

	public void addRequire(Requirement req) {
		requirements.add(req);
	}

	public void addCapability(Capability cap) {
		capabilities.add(cap);
	}

	public void addCategory(String name) {
		categories.add(name);
	}
	
	@Override
	public String toString() {
		return getSymbolicName() + "_" + getVersion().toString();
	}

}
