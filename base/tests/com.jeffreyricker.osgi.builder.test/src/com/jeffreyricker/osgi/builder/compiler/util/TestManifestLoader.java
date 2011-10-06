/**
 * 
 */
package com.jeffreyricker.osgi.builder.compiler.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.junit.Test;
import org.osgi.framework.Constants;

import com.jeffreyricker.osgi.manifest.ManifestLoader;

/**
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class TestManifestLoader {

	@Test
	public void testLoad() throws IOException {
		Manifest manifest = ManifestLoader.load(new File("com.nomura.test"));
		assertNotNull(manifest);
		Attributes attributes = manifest.getMainAttributes();
		assertNotNull(attributes);
		String v = attributes.getValue(Constants.BUNDLE_VERSION);
		assertEquals("1.0.0.qualifier", v);
	}

	@Test
	public void testModify() throws IOException {
		Manifest manifest = ManifestLoader.load(new File("com.nomura.test"));
		Attributes attributes = manifest.getMainAttributes();
		attributes.putValue(Constants.BUNDLE_VERSION, "1.1.0");
		attributes = manifest.getMainAttributes();
		String v = attributes.getValue(Constants.BUNDLE_VERSION);
		assertEquals("1.1.0", v);
	}

}
