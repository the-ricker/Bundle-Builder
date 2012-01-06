package com.jeffreyricker.osgi.builder.source.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.jar.Manifest;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.manifest.ManifestLoader;

/**
 * Creates the compile instructions from the standard Eclipse
 * <code>build.properties</code> file.
 * 
 * @author Ricker
 * @date Apr 26, 2011
 * 
 */
public class EclipseBundleSource implements BundleSource {

	public final static String BUILD_PROPERTIES = "build.properties";
	public final static String SOURCES = "source..";
	public final static String OUTPUT = "output..";
	public final static String BIN_INCLUDES = "bin.includes";

	public final static String DELIMITER = ",";

	private Manifest manifest;
	private File project;
	private Properties prop;
	private List<File> sources;
	private File output;
	private Map<String, File> binIncludes;
	
	private Set<Resource> dependencies;

	/**
	 * Creates compile instructions from a standard Eclipse plug-in (bundle)
	 * project.
	 * 
	 * @param project
	 *            the directory of the Eclipse project
	 */
	public EclipseBundleSource(File project) {
		this.project = project;
	}

	private void load() {
		prop = new Properties();
		try {
			InputStream in = new FileInputStream(new File(project, BUILD_PROPERTIES));
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace(); // TODO
		}
	}

	@Override
	public List<File> getSources() {
		if (sources == null) {
			createSources();
		}
		return sources;
	}

	private void createSources() {
		if (prop == null) {
			load();
		}
		sources = new ArrayList<File>();
		String value = prop.getProperty(SOURCES);
		if (value != null) {
			for (String f : value.split(DELIMITER)) {
				sources.add(new File(project, f));
			}
		}
	}

	@Override
	public File getOutput() {
		if (output == null) {
			createOutput();
		}
		return output;
	}

	private void createOutput() {
		if (prop == null) {
			load();
		}
		String value = prop.getProperty(OUTPUT);
		if (value != null) {
			output = new File(project, value);
		}
	}

	@Override
	public Map<String, File> getBinIncludes() {
		if (binIncludes == null) {
			createBinIncludes();
		}
		return binIncludes;
	}

	private void createBinIncludes() {
		if (prop == null) {
			load();
		}
		binIncludes = new HashMap<String, File>();
		String value = prop.getProperty(BIN_INCLUDES);
		if (value != null) {
			for (String f : value.split(DELIMITER)) {
				if (!f.equals(".")) {
					binIncludes.put(f, new File(project, f));
				}
			}
		}
	}

	@Override
	public Manifest getManifest() throws IOException {
		if (manifest == null) {
			manifest = ManifestLoader.load(project);
		}
		return manifest;
	}

	

	@Override
	public Set<Resource> getDependencies() {
		return dependencies;
	}

	@Override
	public void setDependencies(Set<Resource> dependencies) {
		this.dependencies = dependencies;
	}
	

}
