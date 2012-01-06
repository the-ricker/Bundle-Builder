package com.jeffreyricker.osgi.builder.packager.impl;

import java.io.File;

import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.builder.packager.PackagerJob;
import com.jeffreyricker.osgi.builder.source.BundleSource;

public class BundlePackagerImpl implements BundlePackager {

	@Override
	public PackagerJob createPackager(BundleSource source, File targetDir) {
		return new PackagerJobImpl(source,targetDir);
	}

}
