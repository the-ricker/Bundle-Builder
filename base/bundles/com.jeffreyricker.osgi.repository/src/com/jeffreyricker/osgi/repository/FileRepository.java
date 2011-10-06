/**
 * 
 */
package com.jeffreyricker.osgi.repository;

import java.io.File;
import java.io.IOException;
import java.util.jar.Manifest;

import com.jeffreyricker.osgi.manifest.ManifestLoader;

/**
 * Create a repository from a directory containing bundles.
 * 
 * @author Ricker
 * @date May 5, 2011
 */
public class FileRepository extends SimpleRepository {
	
	

	public FileRepository(File file) throws IOException {
		this(file.getName(), file);
	}

	public FileRepository(String name, File file) throws IOException {
		super(name, file.toURI().toURL());
		load(file);
	}

	protected void load(File file) throws IOException {
		if (file == null || !file.exists() || !file.isDirectory()) {
			throw new IOException("Not a directory");
		}
		SimpleResource resource;
		for (File jar : file.listFiles(new JarFilter())) {
			try {
				resource = new SimpleResource(this, jar.toURI().toURL());
				Manifest manifest = ManifestLoader.load(jar);
				ResourceLoader.populate(resource, manifest);
				this.addResource(resource);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
