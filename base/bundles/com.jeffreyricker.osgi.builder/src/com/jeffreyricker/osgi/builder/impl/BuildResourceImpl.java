package com.jeffreyricker.osgi.builder.impl;

import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.osgi.service.obr.Repository;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.repository.SimpleResource;

/**
 * 
 * @author Ricker
 * @date Apr 28, 2011
 * 
 */
public class BuildResourceImpl extends SimpleResource implements BuildResource {

	private State state;
	private BundleSource source;
	private URL url;
	private Set<BuildResource> parents;
	private Set<BuildResource> children;
	private List<Diagnostic<? extends JavaFileObject>> compilerResults;
	private List<String> resolverResults;
	private File jar;
	
	public BuildResourceImpl(Repository repository, BundleSource source) {
		super(repository, null);
		this.source = source;
		this.state = State.Unresolved;
		parents = new HashSet<BuildResource>();
		children = new HashSet<BuildResource>();
		jar = null;
		resolverResults = null;
		compilerResults = null;
	}

	@Override
	public State getState() {
		return state;
	}

	public void setState(State state) {
		System.out.println(getId() + " state change from " + this.state + " to " + state);
		this.state = state;
	}

	@Override
	public BundleSource getSource() {
		return source;
	}

	public void setURL(URL url) {
		this.url = url;
	}

	@Override
	public URL getURL() {
		return url;
	}

	@Override
	public Set<BuildResource> getParents() {
		return parents;
	}

	@Override
	public Set<BuildResource> getChildren() {
		return children;
	}

	@Override
	public List<Diagnostic<? extends JavaFileObject>> getCompilerResults() {
		return compilerResults;
	}

	@Override
	public void setCompilerResults(List<Diagnostic<? extends JavaFileObject>> compilerResults) {
		this.compilerResults = compilerResults;
	}

	@Override
	public List<String> getResolverResults() {
		return resolverResults;
	}

	@Override
	public void setResolverResults(List<String> resolverResults) {
		this.resolverResults = resolverResults;
	}

	@Override
	public File getFile() {
		return jar;
	}

	@Override
	public void setFile(File jar) {
		this.jar = jar;
	}

	public boolean isReady() {
		for (BuildResource parent : parents) {
			if (parent.getState() != BuildResource.State.Built) {
				return false;
			}
		}
		return true;
	}
	
}
