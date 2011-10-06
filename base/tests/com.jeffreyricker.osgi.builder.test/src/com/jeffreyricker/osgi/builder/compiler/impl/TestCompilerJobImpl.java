/**
 * 
 */
package com.jeffreyricker.osgi.builder.compiler.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.junit.Before;
import org.junit.Test;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.builder.source.util.EclipseBundleSource;


/**
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public class TestCompilerJobImpl {

	static String jarpath = "lib/org.eclipse.osgi_3.6.2.R36x_v20110210.jar";
	BundleSource source;

	@Before
	public void prep() throws Exception {
		source = new EclipseBundleSource(new File("com.nomura.test"));
		// delete bin
		File f = new File("com.nomura.test/bin");
		if (f.exists()) {
			f.delete();
		}
		// dependencies
		Resource resource = createMock(Resource.class);
		File file = new File(jarpath);
		expect(resource.getURL()).andReturn(file.toURI().toURL());
		replay(resource);
		Set<Resource> dep = new HashSet<Resource>();
		dep.add(resource);
		source.setDependencies(dep);
	}

	@Test(expected = IllegalStateException.class)
	public void testBadState() throws Exception {
		source.setDependencies(null);
		CompilerJobImpl job = new CompilerJobImpl(source);
		job.call();
	}

	/**
	 * This will throw a NPE if not using JDK
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		CompilerJobImpl job = new CompilerJobImpl(source);
		List<Diagnostic<? extends JavaFileObject>> result = job.call();
		assertNotNull(result);
		File f = new File("com.nomura.test/bin/com/nomura/test/Activator.class");
		assertTrue(f.exists());
	}

}
