/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.repository.SimpleResource;

/**
 * @author Ricker
 * @date May 2, 2011
 */
public class TestBuildRepositoryImpl {

	BuildRepositoryImpl repo;
	BuildResourceImpl r1;
	BuildResourceImpl r2;
	BuildResourceImpl r3;

	@Before
	public void setup() {
		repo = new BuildRepositoryImpl(null, null);
		r1 = new BuildResourceImpl(repo, null);
		repo.addResource(r1);
		r2 = new BuildResourceImpl(repo, null);
		repo.addResource(r2);
		r3 = new BuildResourceImpl(repo, null);
		repo.addResource(r3);
	}
	
	@Test
	public void testAddBuildResource() {
		assertNotNull(repo.getBuildResources());
		assertEquals(3, repo.getBuildResources().size());
	}

	@Test
	public void testAddNonBuildResource() {
		SimpleResource resource = new SimpleResource(repo, null);
		repo.addResource(resource);
		assertEquals(4, repo.getResources().length);
		assertEquals(3, repo.getBuildResources().size());
	}

	@Test
	public void testHasWork() {
		r1.setState(BuildResource.State.Resolved);
		r2.setState(BuildResource.State.Compiled);
		r3.setState(BuildResource.State.Packaging);
		assertTrue(repo.hasWork());
		//
		r1.setState(BuildResource.State.Packaging);
		r2.setState(BuildResource.State.FailedCompile);
		r3.setState(BuildResource.State.Built);
		assertFalse(repo.hasWork());
		//
		r1.setState(BuildResource.State.Built);
		r2.setState(BuildResource.State.FailedCompile);
		r3.setState(BuildResource.State.Built);
		assertFalse(repo.hasWork());
	}

	@Test
	public void testIsBuilding() {
		r1.setState(BuildResource.State.Resolved);
		r2.setState(BuildResource.State.Compiled);
		r3.setState(BuildResource.State.Packaging);
		assertTrue(repo.isBuilding());
		//
		r1.setState(BuildResource.State.Packaging);
		r2.setState(BuildResource.State.FailedCompile);
		r3.setState(BuildResource.State.Built);
		assertTrue(repo.isBuilding());
		//
		r1.setState(BuildResource.State.Built);
		r2.setState(BuildResource.State.FailedCompile);
		r3.setState(BuildResource.State.Built);
		assertFalse(repo.isBuilding());
	}


}
