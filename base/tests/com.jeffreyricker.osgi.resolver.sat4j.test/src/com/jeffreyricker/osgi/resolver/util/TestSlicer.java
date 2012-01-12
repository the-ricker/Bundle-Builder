/**
 * 
 */
package com.jeffreyricker.osgi.resolver.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.repository.FileRepository;

/**
 * @author Ricker
 * @date May 6, 2011
 */
public class TestSlicer {
	
	FileRepository repository;
	
	@Before
	public void setUp() throws IOException {
		repository = new FileRepository(new File("repository/"));
	}
	
	@Test
	public void testAlpha() {
		Resource r = getResource("com.nomura.test.alpha");
		assertNotNull(r);
		Set<Resource> slice = Slicer.slice(repository, r);
		assertNotNull(slice);
		assertEquals(0, slice.size());
	}
	
	@Test
	public void testBeta() {
		Resource r = getResource("com.nomura.test.beta");
		assertNotNull(r);
		Set<Resource> slice = Slicer.slice(repository, r);
		assertNotNull(slice);
		assertEquals(2, slice.size());
	}
	
	@Test
	public void testGamma() {
		Resource r = getResource("com.nomura.test.gamma");
		assertNotNull(r);
		Set<Resource> slice = Slicer.slice(repository, r);
		assertNotNull(slice);
		assertEquals(3, slice.size());
	}
	
	Resource getResource(String symbolicName) {
		for (Resource r : repository.getResources()) {
			if (symbolicName.equals(r.getSymbolicName())) {
				return r;
			}
		}
		return null;
	}

}
