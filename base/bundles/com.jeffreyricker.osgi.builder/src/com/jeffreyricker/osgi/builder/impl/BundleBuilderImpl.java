/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jeffreyricker.osgi.builder.BuildInstructions;
import com.jeffreyricker.osgi.builder.BundleBuilder;
import com.jeffreyricker.osgi.builder.BundleBuilderJob;
import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.resolver.BundleResolver;

/**
 * @author Ricker
 * @date Apr 29, 2011
 * 
 */
public class BundleBuilderImpl implements BundleBuilder {

	private BundleResolver resolver;
	private BundleCompiler compiler;
	private BundlePackager packager;

	public void setResolver(BundleResolver resolver) {
		this.resolver = resolver;
	}

	public void setCompiler(BundleCompiler compiler) {
		this.compiler = compiler;
	}

	public void setPackager(BundlePackager packager) {
		this.packager = packager;
	}

	public BundleResolver getResolver() {
		return resolver;
	}

	public BundleCompiler getCompiler() {
		return compiler;
	}

	public BundlePackager getPackager() {
		return packager;
	}

	@Override
	public BundleBuilderJob createBuilder(BuildInstructions instructions) {
		return new BundleBuilderJobImpl(instructions, Executors.newCachedThreadPool(), resolver, compiler, packager);
	}

	@Override
	public BundleBuilderJob createBuilder(BuildInstructions instructions, Executor executor) {
		return new BundleBuilderJobImpl(instructions, executor, resolver, compiler, packager);
	}

	@Override
	public BundleBuilderJob createBuilder(BuildInstructions instructions, ExecutorService executor,
			BundleResolver resolver, BundleCompiler compiler, BundlePackager packager) {
		return new BundleBuilderJobImpl(instructions, executor, resolver, compiler, packager);
	}

}
