package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeffreyricker.osgi.builder.BuildResource;

public class TestBuildResourceImpl {
	
	@Test
	public void testState() {
		BuildResourceImpl resource = new BuildResourceImpl(null,null);
		assertNotNull(resource);
		assertEquals(BuildResource.State.Unresolved, resource.getState());
		resource.setState(BuildResource.State.Resolved);
		assertEquals(BuildResource.State.Resolved, resource.getState());
	}

	@Test
	public void testConstructor() {
		BuildResourceImpl resource = new BuildResourceImpl(null,null);
		assertNotNull(resource);
		
	}
	
}
