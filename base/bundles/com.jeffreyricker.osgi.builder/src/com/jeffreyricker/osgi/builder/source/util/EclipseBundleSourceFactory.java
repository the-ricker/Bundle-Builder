/**
 * 
 */
package com.jeffreyricker.osgi.builder.source.util;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Set;

import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.builder.source.BundleSourceFactory;

/**
 * Will take either an Eclipse project or a directory that contains Eclipse projects
 * @author Ricker
 * @date May 2, 2011
 */
public class EclipseBundleSourceFactory implements BundleSourceFactory {

	@Override
	public Set<BundleSource> createSource(File file) {
		FileFilter filter = new EclipseProjectFilter();
		
		Set<BundleSource> set = new HashSet<BundleSource>();
		if (filter.accept(file)) {
			set.add(new EclipseBundleSource(file));
		} else {
			for (File f : file.listFiles(filter)) {
				set.add(new EclipseBundleSource(f));
			}
		}
		return set;
	}

	
}
