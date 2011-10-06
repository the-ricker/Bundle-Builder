package com.jeffreyricker.osgi.builder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.resolver.BundleResolver;

public interface BundleBuilder {

	public BundleBuilderJob createBuilder(BuildInstructions instructions);

	public BundleBuilderJob createBuilder(BuildInstructions instructions, Executor executor);

	public BundleBuilderJob createBuilder(BuildInstructions instructions, ExecutorService executor,
			BundleResolver resolver, BundleCompiler compiler, BundlePackager packager);

}
