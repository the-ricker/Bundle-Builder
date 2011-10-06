package com.jeffreyricker.osgi.repository;

import java.io.File;
import java.io.FileFilter;

public class JarFilter implements FileFilter {
	
	public final static String EXTENSION = ".jar";

	@Override
	public boolean accept(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			return false;
		}
		return file.getName().endsWith(EXTENSION);
	}
	
}