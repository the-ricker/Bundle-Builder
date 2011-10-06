package com.jeffreyricker.osgi.builder.packager.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.builder.source.util.EclipseBundleSource;

public class TestPackagerJob {
	

	BundleSource bundleSource;
	
	@Before
	public void setup() {
		bundleSource = new EclipseBundleSource(new File("com.nomura.test"));
	}

	@Test
	public void testJar() throws Exception {
		PackagerJobImpl job = new PackagerJobImpl(bundleSource, new File("output"));
		File jar = job.call();
		assertNotNull(jar);
		assertTrue(jar.isFile());
		assertTrue(jar.exists());
	}

}
