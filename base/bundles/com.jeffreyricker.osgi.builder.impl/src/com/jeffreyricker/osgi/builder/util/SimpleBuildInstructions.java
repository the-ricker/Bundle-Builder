/**
 * 
 */
package com.jeffreyricker.osgi.builder.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.osgi.service.obr.Repository;

import com.jeffreyricker.osgi.builder.BuildInstructions;
import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * @author Ricker
 * @date Apr 28, 2011
 * 
 */
public class SimpleBuildInstructions implements BuildInstructions {

	public final static DateFormat DATE_TIME_QUALIFIER = new SimpleDateFormat("yyyyMMddHHmm");

	private String versionQualifier;
	private Repository repository;
	private File outputDirectory;
	private boolean parallel;
	private Set<BundleSource> buildSources;

	public SimpleBuildInstructions() {
		parallel = true;
		versionQualifier = DATE_TIME_QUALIFIER.format(new Date());
	}

	@Override
	public String getVersionQualifier() {
		return versionQualifier;
	}

	@Override
	public Repository getRepository() {
		return repository;
	}

	@Override
	public File getOutputDirectory() {
		return outputDirectory;
	}

	@Override
	public boolean isParallel() {
		return parallel;
	}

	@Override
	public Set<BundleSource> getBundleSources() {
		return buildSources;
	}

	public void setVersionQualifier(String versionQualifier) {
		this.versionQualifier = versionQualifier;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}

	public void setBundleSources(Set<BundleSource> buildSources) {
		this.buildSources = buildSources;
	}

	@Override
	public URL getOutputURL() throws MalformedURLException {
		return outputDirectory.toURI().toURL();
	}

}
