/**
 * 
 */
package com.jeffreyricker.osgi.builder.source.util;

import java.io.File;
import java.io.FileFilter;
import java.util.jar.JarFile;

/**
 * Must be a directory that contains a manifest and build properties
 * @author Ricker
 * @date May 5, 2011
 */
public class EclipseProjectFilter implements FileFilter {

	@Override
	public boolean accept(File file) {
		if (file == null || !file.exists() || !file.isDirectory()) {
			return false;
		}
		File f = new File(file, JarFile.MANIFEST_NAME);
		if (f == null || !f.exists() || !f.isFile()) {
			return false;
		}
		f = new File(file, EclipseBundleSource.BUILD_PROPERTIES);
		if (f == null || !f.exists() || !f.isFile()) {
			return false;
		}
		return true;
	}

}
