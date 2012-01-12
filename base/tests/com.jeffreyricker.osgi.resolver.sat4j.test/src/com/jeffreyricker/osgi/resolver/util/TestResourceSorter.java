/**
 * 
 */
package com.jeffreyricker.osgi.resolver.util;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import org.osgi.framework.Version;
import org.osgi.service.obr.Resource;

import org.junit.Test;

/**
 * @author jricker
 *
 */
public class TestResourceSorter {
	
	@Test
	public void testNullLeft() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		Resource left = null;
		assertEquals(-1, sorter.compare(left, right));
	}
	
	@Test
	public void testNullRight() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = null;
		Resource left = createMock(Resource.class);
		assertEquals(1, sorter.compare(left, right));
	}
	
	@Test
	public void testNulls() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = null;
		Resource left = null;
		assertEquals(0, sorter.compare(left, right));
	}
	
	@Test
	public void testNames() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		expect(right.getSymbolicName()).andReturn("Alpha");
		replay(right);
		Resource left = createMock(Resource.class);
		expect(left.getSymbolicName()).andReturn("Beta");
		replay(left);
		assertEquals(1, sorter.compare(left, right));
	}

	@Test
	public void testVersion() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		expect(right.getSymbolicName()).andReturn("Alpha");
		expect(right.getVersion()).andReturn(new Version("1.0.0"));
		replay(right);
		Resource left = createMock(Resource.class);
		expect(left.getSymbolicName()).andReturn("Alpha");
		expect(left.getVersion()).andReturn(new Version("2.0.0"));
		replay(left);
		assertEquals(-1, sorter.compare(left, right));
	}
	
	@Test
	public void testVersionMinor() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		expect(right.getSymbolicName()).andReturn("Alpha");
		expect(right.getVersion()).andReturn(new Version("1.0.1"));
		replay(right);
		Resource left = createMock(Resource.class);
		expect(left.getSymbolicName()).andReturn("Alpha");
		expect(left.getVersion()).andReturn(new Version("1.0.2"));
		replay(left);
		assertEquals(-1, sorter.compare(left, right));
	}
	
	@Test
	public void testVersionQualifier() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		expect(right.getSymbolicName()).andReturn("Alpha");
		expect(right.getVersion()).andReturn(new Version("1.0.1.alpha"));
		replay(right);
		Resource left = createMock(Resource.class);
		expect(left.getSymbolicName()).andReturn("Alpha");
		expect(left.getVersion()).andReturn(new Version("1.0.1.beta"));
		replay(left);
		assertEquals(-1, sorter.compare(left, right));
	}
	
	@Test
	public void testVersionQualifierDate() {
		ResourceSorter sorter = new ResourceSorter();
		Resource right = createMock(Resource.class);
		expect(right.getSymbolicName()).andReturn("Alpha");
		expect(right.getVersion()).andReturn(new Version("1.0.1.20110105"));
		replay(right);
		Resource left = createMock(Resource.class);
		expect(left.getSymbolicName()).andReturn("Alpha");
		expect(left.getVersion()).andReturn(new Version("1.0.1.20110106"));
		replay(left);
		assertEquals(-1, sorter.compare(left, right));
	}
	
}
