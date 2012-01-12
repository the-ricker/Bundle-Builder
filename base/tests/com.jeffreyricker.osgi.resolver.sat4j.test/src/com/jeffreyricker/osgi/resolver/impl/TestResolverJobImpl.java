/**
 * 
 */
package com.jeffreyricker.osgi.resolver.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.repository.FileRepository;
import com.jeffreyricker.osgi.resolver.Solution;

/**
 * @author Ricker
 * @date May 5, 2011
 */
public class TestResolverJobImpl {

	FileRepository repository;
	Resource core;
	Resource resource;

	@Before
	public void prep() throws IOException {
		repository = new FileRepository(new File("repository/"));
		core = getResource("org.eclipse.osgi");
		resource = getResource("com.nomura.test.gamma");
	}

	@Test
	public void test() throws Exception {
		assertNotNull(resource);
		ResolverJobImpl job = new ResolverJobImpl(repository, resource);
		assertNotNull(job);
		Solution solution = job.call();
		assertNotNull(solution);
		assertNotNull(solution.getDependencies());
		assertEquals(3, solution.getDependencies().size());
		assertTrue(solution.getDependencies().contains(core));
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
