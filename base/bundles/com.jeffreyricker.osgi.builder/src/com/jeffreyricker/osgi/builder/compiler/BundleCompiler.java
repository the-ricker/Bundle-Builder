package com.jeffreyricker.osgi.builder.compiler;

import com.jeffreyricker.osgi.builder.source.BundleSource;


public interface BundleCompiler {
	
	public CompilerJob createCompiler(BundleSource source);

}
