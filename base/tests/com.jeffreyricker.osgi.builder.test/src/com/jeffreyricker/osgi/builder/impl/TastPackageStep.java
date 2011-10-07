package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.packager.PackagerJob;

/**
 * 
 * @author Ricker
 * @date May 2, 2011
 */
public class TastPackageStep {
	
	@Test
	public void testSuccess() throws Exception {
		BuildResource resource = new BuildResourceImpl(null,null);
		PackagerJob packager = EasyMock.createMock(PackagerJob.class);
		EasyMock.expect(packager.call()).andReturn(null);
		EasyMock.replay(packager);
		PackageStep job = new PackageStep(resource, packager);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(BuildResource.State.Built, result.getState());
	}
	
	@Test
	public void testFail() throws Exception {
		BuildResource resource = new BuildResourceImpl(null,null);
		PackagerJob packager = EasyMock.createMock(PackagerJob.class);
		EasyMock.expect(packager.call()).andThrow(new Exception());
		EasyMock.replay(packager);
		PackageStep job = new PackageStep(resource, packager);
		BuildResource result = job.call();
		assertNotNull(result);
		assertTrue(resource == result);
		assertEquals(BuildResource.State.FailedPackaging, result.getState());
	}
}
