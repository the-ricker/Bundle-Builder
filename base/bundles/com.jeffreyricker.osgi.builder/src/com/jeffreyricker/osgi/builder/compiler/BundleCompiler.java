package com.jeffreyricker.osgi.builder.compiler;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * Service for compiling the Java classes in a bundle.
 * 
 * @author ricker
 *
 */
public interface BundleCompiler {
	
	public CompilerJob createCompiler(BundleSource source);

}
