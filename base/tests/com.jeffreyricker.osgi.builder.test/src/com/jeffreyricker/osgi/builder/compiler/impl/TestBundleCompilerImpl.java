/**
 * 
 */
package com.jeffreyricker.osgi.builder.compiler.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.compiler.CompilerJob;
import com.jeffreyricker.osgi.builder.compiler.impl.BundleCompilerImpl;

/**
 * @author Ricker
 * @date Apr 27, 2011
 *
 */
public class TestBundleCompilerImpl {
	
	@Test
	public void test() {
		BundleCompiler factory = new BundleCompilerImpl();
		assertNotNull(factory);
		Object obj1 = factory.createCompiler(null);
		assertNotNull(obj1);
		assertTrue(obj1 instanceof CompilerJob);
		Object obj2 = factory.createCompiler(null);
		assertNotNull(obj2);
		assertFalse(obj1 == obj2);
	}

}
