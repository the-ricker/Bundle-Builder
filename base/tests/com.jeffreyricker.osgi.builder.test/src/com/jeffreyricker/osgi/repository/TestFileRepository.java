/**
 * 
 */
package com.jeffreyricker.osgi.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.osgi.service.obr.Capability;
import org.osgi.service.obr.Resource;

/**
 * @author Ricker
 * @date May 5, 2011
 */
public class TestFileRepository {
	
	@Test
	public void test() throws IOException {
		FileRepository repository = new FileRepository(new File("lib/"));
		assertNotNull(repository);
		Resource[] resources = repository.getResources();
		assertNotNull(resources);
		assertEquals(1, resources.length);
		assertTrue(resources[0] instanceof SimpleResource);
		Capability[] capabilities = resources[0].getCapabilities();
		assertNotNull(capabilities);
		assertTrue(capabilities.length > 0);
	}

}
