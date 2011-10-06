package com.jeffreyricker.osgi.builder.compiler.util;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.compiler.util.ClassPaths;

public class TestClassPaths {
	
	static String jarpath = "lib/org.eclipse.osgi_3.6.2.R36x_v20110210.jar";
	
	Resource resource;
	
	
	@Before
	public void setup() throws Exception {
		resource = createMock(Resource.class);
		File file = new File(jarpath);
		expect(resource.getURL()).andReturn(file.toURI().toURL());
		replay(resource);
	}
	
	@Test
	public void testCreateClassPath() {
		Set<Resource> dependencies = new HashSet<Resource>();
		dependencies.add(resource);
		String path = ClassPaths.createClassPath(dependencies);
		System.out.println(path);
		assertTrue(path.contains(jarpath));
	}
	
	@Test
	public void testCreateClassPathNoSystem() {
		Set<Resource> dependencies = new HashSet<Resource>();
		dependencies.add(resource);
		String path = ClassPaths.createClassPath(dependencies, false);
		System.out.println(path);
		assertTrue(path.contains(jarpath));
	}
	
	@Test
	public void testNoLeadSemicolon() {
		Set<Resource> dependencies = new HashSet<Resource>();
		dependencies.add(resource);
		String path = ClassPaths.createClassPath(dependencies);
		assertFalse(path.startsWith(";"));
	}


}
