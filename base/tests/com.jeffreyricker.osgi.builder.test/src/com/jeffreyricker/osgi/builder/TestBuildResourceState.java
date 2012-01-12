package com.jeffreyricker.osgi.builder;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBuildResourceState {

	@Test
	public void testHasWork() {
		assertTrue(ResourceState.Unresolved.hasWork());
		assertTrue(ResourceState.Resolving.hasWork());
		assertFalse(ResourceState.FailedResolve.hasWork());
		assertTrue(ResourceState.Resolved.hasWork());
//		assertTrue(ResourceState.Ready.hasWork());
		assertTrue(ResourceState.Compiling.hasWork());
		assertFalse(ResourceState.FailedCompile.hasWork());
		assertTrue(ResourceState.Compiled.hasWork());
		assertFalse(ResourceState.Packaging.hasWork());
		assertFalse(ResourceState.FailedPackaging.hasWork());
		assertFalse(ResourceState.Built.hasWork());
	}
	
	@Test
	public void testIsWorking() {
		assertTrue(ResourceState.Unresolved.isWorking());
		assertTrue(ResourceState.Resolving.isWorking());
		assertFalse(ResourceState.FailedResolve.isWorking());
		assertTrue(ResourceState.Resolved.isWorking());
//		assertTrue(ResourceState.Ready.isWorking());
		assertTrue(ResourceState.Compiling.isWorking());
		assertFalse(ResourceState.FailedCompile.isWorking());
		assertTrue(ResourceState.Compiled.isWorking());
		assertTrue(ResourceState.Packaging.isWorking());
		assertFalse(ResourceState.FailedPackaging.isWorking());
		assertFalse(ResourceState.Built.isWorking());
	}

	
	@Test
	public void testIsFailed() {
		assertFalse(ResourceState.Unresolved.isFailed());
		assertFalse(ResourceState.Resolving.isFailed());
		assertTrue(ResourceState.FailedResolve.isFailed());
		assertFalse(ResourceState.Resolved.isFailed());
//		assertFalse(ResourceState.Ready.isFailed());
		assertFalse(ResourceState.Compiling.isFailed());
		assertTrue(ResourceState.FailedCompile.isFailed());
		assertFalse(ResourceState.Compiled.isFailed());
		assertFalse(ResourceState.Packaging.isFailed());
		assertTrue(ResourceState.FailedPackaging.isFailed());
		assertFalse(ResourceState.Built.isFailed());
	}


}
