package com.jeffreyricker.osgi.builder.compiler.impl;

import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.compiler.CompilerJob;
import com.jeffreyricker.osgi.builder.source.BundleSource;

public class BundleCompilerImpl implements BundleCompiler {

	@Override
	public CompilerJob createCompiler(BundleSource source) {
		return new CompilerJobImpl(source);
	}

}
