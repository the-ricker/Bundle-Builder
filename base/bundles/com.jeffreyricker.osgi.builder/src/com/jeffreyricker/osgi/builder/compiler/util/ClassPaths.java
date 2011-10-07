/**
 * 
 */
package com.jeffreyricker.osgi.builder.compiler.util;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.osgi.service.obr.Resource;

/**
 * Utility class for working with Java class paths.
 * 
 * @author Ricker
 * 
 */
public class ClassPaths {

	private static FileFilter javaFileFilter = new FileFilter() {

		@Override
		public boolean accept(File file) {
			return file.isFile() && file.exists()
					&& file.getName().endsWith(".java");
		}

	};

	private static FileFilter dirFileFilter = new FileFilter() {

		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}

	};

	/**
	 * Creates a Java class path string from a set of resources.
	 * 
	 * @param dependencies
	 * @param includeSystem
	 *            include the system class path
	 * @return
	 */
	public static String createClassPath(Set<Resource> dependencies,
			boolean includeSystem) {
		StringBuilder buf = new StringBuilder();
		boolean first = true;
		if (includeSystem) {
			buf.append(System.getProperty("java.class.path"));
			first = false;
		}
		for (Resource dep : dependencies) {
			URL url = dep.getURL();
			if (url != null) {
				if (!first) {
					buf.append(File.pathSeparator);
				}
				buf.append(url.toString());
				first = false;
			}
		}
		return buf.toString();
	}

	/**
	 * Creates a Java class path string from a set of resources.
	 * 
	 * @param dependencies
	 * @return
	 */
	public static String createClassPath(Set<Resource> dependencies) {
		return createClassPath(dependencies, true);
	}

	/**
	 * Creates a URL class loader from a set of dependencies.
	 * 
	 * @param dependencies
	 * @return
	 */
	public static ClassLoader createClassLoader(Set<Resource> dependencies) {
		List<URL> urls = new ArrayList<URL>();
		for (Resource dep : dependencies) {
			URL url = dep.getURL();
			if (url != null) {
				System.out.println(url.toString());
				urls.add(url);
			}
		}
		return new URLClassLoader(urls.toArray(new URL[0]));
	}

	/**
	 * Create a list of all the Java files under the given root directory.
	 * Searches sub-directories recursively.
	 * 
	 * @param root
	 * @return
	 */
	public static List<File> findJavaFiles(File root) {
		return findJavaFiles(root, new ArrayList<File>());
	}

	private static List<File> findJavaFiles(File parent, List<File> list) {
		for (File f : parent.listFiles(javaFileFilter)) {
			list.add(f);
		}
		for (File d : parent.listFiles(dirFileFilter)) {
			findJavaFiles(d, list);
		}
		return list;
	}

}
