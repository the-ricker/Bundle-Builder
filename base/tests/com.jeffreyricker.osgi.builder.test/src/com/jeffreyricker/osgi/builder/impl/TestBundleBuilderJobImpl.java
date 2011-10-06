package com.jeffreyricker.osgi.builder.impl;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Set;
import java.util.concurrent.Executors;

import org.junit.Test;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.compiler.impl.BundleCompilerImpl;
import com.jeffreyricker.osgi.builder.packager.impl.BundlePackagerImpl;
import com.jeffreyricker.osgi.builder.source.util.EclipseBundleSourceFactory;
import com.jeffreyricker.osgi.builder.util.SimpleBuildInstructions;
import com.jeffreyricker.osgi.repository.FileRepository;
import com.jeffreyricker.osgi.resolver.impl.BundleResolverImpl;

public class TestBundleBuilderJobImpl {

	@Test
	public void testFake() throws Exception {
		SimpleBuildInstructions instructions = new SimpleBuildInstructions();
		instructions.setRepository(new FileRepository(new File("lib/")));
		instructions.setOutputDirectory(new File("example/output"));
		EclipseBundleSourceFactory sourceFactory = new EclipseBundleSourceFactory();
		instructions.setBundleSources(sourceFactory.createSource(new File("example/projects/")));
		DummyJobFactory jobfactory = new DummyJobFactory();
		BundleBuilderJobImpl job = new BundleBuilderJobImpl(instructions, Executors.newCachedThreadPool(),
				new BundleResolverImpl(), jobfactory, jobfactory);
		Set<BuildResource> results = job.call();
		assertNotNull(results);
	}

	@Test
	public void testReal() throws Exception {
		SimpleBuildInstructions instructions = new SimpleBuildInstructions();
		instructions.setRepository(new FileRepository(new File("lib/")));
		instructions.setOutputDirectory(new File("example/output"));
		EclipseBundleSourceFactory sourceFactory = new EclipseBundleSourceFactory();
		instructions.setBundleSources(sourceFactory.createSource(new File("example/projects/")));
		BundleBuilderJobImpl job = new BundleBuilderJobImpl(instructions, Executors.newCachedThreadPool(),
				new BundleResolverImpl(), new BundleCompilerImpl(), new BundlePackagerImpl());
		Set<BuildResource> results = job.call();
		assertNotNull(results);
	}

}
