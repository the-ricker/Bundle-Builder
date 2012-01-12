/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.ResourceState;
import com.jeffreyricker.osgi.builder.compiler.CompilerJob;

/**
 * @author Ricker
 * @date May 2, 2011
 */
public class TestCompileStep {
	
	
	@Test
	public void testSuccess() throws Exception {
		BuildResource resource = new BuildResourceImpl(null,null);
		CompilerJob compiler = EasyMock.createMock(CompilerJob.class);
		EasyMock.expect(compiler.call()).andReturn(null);
		EasyMock.replay(compiler);
		CompileStep job = new CompileStep(resource, compiler);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(ResourceState.Compiled, result.getState());
	}
	
	@Test
	public void testFail() throws Exception {
		BuildResource resource = new BuildResourceImpl(null,null);
		CompilerJob compiler = EasyMock.createMock(CompilerJob.class);
		EasyMock.expect(compiler.call()).andThrow(new Exception());
		EasyMock.replay(compiler);
		CompileStep job = new CompileStep(resource, compiler);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(ResourceState.FailedCompile, result.getState());
	}

}
