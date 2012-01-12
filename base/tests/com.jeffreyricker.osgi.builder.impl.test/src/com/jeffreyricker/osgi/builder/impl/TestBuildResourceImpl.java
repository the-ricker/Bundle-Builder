package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.jeffreyricker.osgi.builder.ResourceState;

public class TestBuildResourceImpl {
	
	@Test
	public void testState() {
		BuildResourceImpl resource = new BuildResourceImpl(null,null);
		assertNotNull(resource);
		assertEquals(ResourceState.Unresolved, resource.getState());
		resource.setState(ResourceState.Resolved);
		assertEquals(ResourceState.Resolved, resource.getState());
	}

	@Test
	public void testConstructor() {
		BuildResourceImpl resource = new BuildResourceImpl(null,null);
		assertNotNull(resource);
		
	}
	
}
