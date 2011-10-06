package com.jeffreyricker.osgi.builder.compiler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.jar.Manifest;

import org.junit.Before;
import org.junit.Test;

import com.jeffreyricker.osgi.builder.source.util.EclipseBundleSource;



/**
 * 
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class TestEclipseBundleSource {

	EclipseBundleSource bundlesource;

	@Before
	public void setup() {
		bundlesource = new EclipseBundleSource(new File("com.nomura.test"));
	}

	@Test
	public void testOutput() {
		File f = bundlesource.getOutput();
		assertNotNull(f);
		assertEquals("bin", f.getName());

	}

	@Test
	public void testSources() {
		List<File> ff = bundlesource.getSources();
		assertNotNull(ff);
		assertEquals(1, ff.size());
		File f = ff.get(0);
		assertNotNull(f);
		assertEquals("src", f.getName());
	}
	
	@Test 
	public void testManifest() throws IOException {
		Manifest manifest = bundlesource.getManifest();
		assertNotNull(manifest);
		String v = manifest.getMainAttributes().getValue("Bundle-SymbolicName");
		assertEquals("com.nomura.test", v);
	}
	
	@Test
	public void testBinIncludes() {
		Map<String,File> items = bundlesource.getBinIncludes();
		assertNotNull(items);
		assertEquals(2, items.size());
		assertNotNull(items.get("README.txt"));
	}

}
