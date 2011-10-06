package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.source.util.SimpleBundleSource;
import com.jeffreyricker.osgi.repository.SimpleResource;
import com.jeffreyricker.osgi.resolver.ResolverJob;
import com.jeffreyricker.osgi.resolver.Solution;
import com.jeffreyricker.osgi.resolver.impl.SolutionImpl;

/**
 * 
 * @author Ricker
 * @date May 2, 2011
 */
public class TestResolveJob {

	BuildResource resource;
	BuildResource d1;
	Resource d2;
	SolutionImpl solution;

	@Before
	public void setup() {
		resource = new BuildResourceImpl(null, new SimpleBundleSource());
		solution = new SolutionImpl(null, resource);
		d1 = new BuildResourceImpl(null, null);
		solution.addDependency(d1);
		d2 = new SimpleResource(null, null);
		solution.addDependency(d2);
	}

	@Test
	public void testSuccess() throws Exception {
		solution.setState(Solution.State.Satisfied);
		ResolverJob resolver = EasyMock.createMock(ResolverJob.class);
		EasyMock.expect(resolver.call()).andReturn(solution);
		EasyMock.replay(resolver);
		ResolveJob job = new ResolveJob(resource, resolver);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(BuildResource.State.Resolved, result.getState());
		assertEquals(1, result.getParents().size());
		assertEquals(0, result.getChildren().size());
		assertEquals(0, d1.getParents().size());
		assertEquals(1, d1.getChildren().size());
	}

	@Test
	public void testFail() throws Exception {
		ResolverJob resolver = EasyMock.createMock(ResolverJob.class);
		EasyMock.expect(resolver.call()).andReturn(null);
		EasyMock.replay(resolver);
		ResolveJob job = new ResolveJob(resource, resolver);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(BuildResource.State.FailedResolve, result.getState());
	}

	@Test
	public void testUnsatisfied() throws Exception {
		solution.setState(Solution.State.Unsatisfied);
		ResolverJob resolver = EasyMock.createMock(ResolverJob.class);
		EasyMock.expect(resolver.call()).andReturn(solution);
		EasyMock.replay(resolver);
		ResolveJob job = new ResolveJob(resource, resolver);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(BuildResource.State.FailedResolve, result.getState());
	}

}
