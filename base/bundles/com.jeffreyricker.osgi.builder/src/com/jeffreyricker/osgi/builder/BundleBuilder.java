package com.jeffreyricker.osgi.builder;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.resolver.BundleResolver;

/**
 * The service for actually building the bundles.
 * 
 * 
 * @author ricker
 * 
 */
public interface BundleBuilder {

	/**
	 * Create a bundle build job with a default executor and the registered
	 * resolver, compiler and packager services.
	 * 
	 * @param instructions
	 * @return
	 */
	public BundleBuilderJob createBuilder(BuildInstructions instructions);

	/**
	 * Create a bundle build job with the given executor and the registered
	 * resolver, compiler and packager services.
	 * 
	 * @param instructions
	 * @param executor
	 * @return
	 */
	public BundleBuilderJob createBuilder(BuildInstructions instructions,
			Executor executor);

	/**
	 * Create a bundle build job with the given executor, resolver, compiler and
	 * packager services.
	 * 
	 * @param instructions
	 * @param executor
	 * @param resolver
	 * @param compiler
	 * @param packager
	 * @return
	 */
	public BundleBuilderJob createBuilder(BuildInstructions instructions,
			ExecutorService executor, BundleResolver resolver,
			BundleCompiler compiler, BundlePackager packager);

}
