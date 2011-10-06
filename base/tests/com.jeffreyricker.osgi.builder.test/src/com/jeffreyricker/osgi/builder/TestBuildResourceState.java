package com.jeffreyricker.osgi.builder;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBuildResourceState {

	@Test
	public void testHasWork() {
		assertTrue(BuildResource.State.Unresolved.hasWork());
		assertTrue(BuildResource.State.Resolving.hasWork());
		assertFalse(BuildResource.State.FailedResolve.hasWork());
		assertTrue(BuildResource.State.Resolved.hasWork());
//		assertTrue(BuildResource.State.Ready.hasWork());
		assertTrue(BuildResource.State.Compiling.hasWork());
		assertFalse(BuildResource.State.FailedCompile.hasWork());
		assertTrue(BuildResource.State.Compiled.hasWork());
		assertFalse(BuildResource.State.Packaging.hasWork());
		assertFalse(BuildResource.State.FailedPackaging.hasWork());
		assertFalse(BuildResource.State.Built.hasWork());
	}
	
	@Test
	public void testIsWorking() {
		assertTrue(BuildResource.State.Unresolved.isWorking());
		assertTrue(BuildResource.State.Resolving.isWorking());
		assertFalse(BuildResource.State.FailedResolve.isWorking());
		assertTrue(BuildResource.State.Resolved.isWorking());
//		assertTrue(BuildResource.State.Ready.isWorking());
		assertTrue(BuildResource.State.Compiling.isWorking());
		assertFalse(BuildResource.State.FailedCompile.isWorking());
		assertTrue(BuildResource.State.Compiled.isWorking());
		assertTrue(BuildResource.State.Packaging.isWorking());
		assertFalse(BuildResource.State.FailedPackaging.isWorking());
		assertFalse(BuildResource.State.Built.isWorking());
	}

	
	@Test
	public void testIsFailed() {
		assertFalse(BuildResource.State.Unresolved.isFailed());
		assertFalse(BuildResource.State.Resolving.isFailed());
		assertTrue(BuildResource.State.FailedResolve.isFailed());
		assertFalse(BuildResource.State.Resolved.isFailed());
//		assertFalse(BuildResource.State.Ready.isFailed());
		assertFalse(BuildResource.State.Compiling.isFailed());
		assertTrue(BuildResource.State.FailedCompile.isFailed());
		assertFalse(BuildResource.State.Compiled.isFailed());
		assertFalse(BuildResource.State.Packaging.isFailed());
		assertTrue(BuildResource.State.FailedPackaging.isFailed());
		assertFalse(BuildResource.State.Built.isFailed());
	}


}
