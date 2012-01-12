/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;

import com.jeffreyricker.osgi.builder.BuildInstructions;
import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.BundleBuilderJob;
import com.jeffreyricker.osgi.builder.ResourceState;
import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.repository.SimpleRepositoryGroup;
import com.jeffreyricker.osgi.resolver.BundleResolver;

/**
 * <pre>
 * 1. Create a repository of the sources
 * 2. For each build resource
 *    a. Resolve the resource
 * 	  b. If the dependencies are all built then 
 * 	  c. Compile the source
 * 	  d. Package the source
 * </pre>
 * 
 * @author Ricker
 * @date Apr 28, 2011
 * 
 */
public class BundleBuilderJobImpl implements BundleBuilderJob {

	ExecutorCompletionService<BuildResource> completionService;
	private BundleResolver resolver;
	private BundleCompiler compiler;
	private BundlePackager packager;
	private BuildInstructions instructions;

	public BundleBuilderJobImpl(BuildInstructions instructions, Executor executor, BundleResolver resolver,
			BundleCompiler compiler, BundlePackager packager) {
		this.instructions = instructions;
		this.resolver = resolver;
		this.compiler = compiler;
		this.packager = packager;
		completionService = new ExecutorCompletionService<BuildResource>(executor);
	}

	@Override
	public Set<BuildResource> call() throws Exception {
		log("Start build");
		/*
		 * create repositories
		 */
		BuildRepositoryImpl buildRepository = new BuildRepositoryImpl(instructions);
		SimpleRepositoryGroup repository = new SimpleRepositoryGroup("Build", null);
		repository.addRepository(buildRepository);
		repository.addRepository(instructions.getRepository());
		log("Repository ready");
		try {
			/*
			 * resolve bundles
			 */
			BuildStep step;
			for (BuildResource resource : buildRepository.getBuildResources()) {
				resource.setState(ResourceState.Resolving);
				step = new ResolveStep((BuildResource) resource, resolver.createResolver(repository, resource));
				completionService.submit(step);
			}
			/*
			 * work bundles through other states
			 */
			while (buildRepository.hasWork()) {
				BuildResource resource = completionService.take().get();
				switch (resource.getState()) {
				case Resolved:
					// compile
					if (resource.isReady()) {
//						resource.setState(BuildResource.State.Compiling);
						step = new CompileStep(resource, compiler.createCompiler(resource.getSource()));
						completionService.submit(step);
					}
					break;
				case Compiled:
					// package
//					resource.setState(BuildResource.State.Packaging);
					step = new PackageStep(resource, packager.createPackager(resource.getSource(),
							instructions.getOutputDirectory()));
					completionService.submit(step);
					break;
				case Built:
					// compile ready children
					for (BuildResource child : resource.getChildren()) {
						if (child.getState() == ResourceState.Resolved && child.isReady()) {
//							child.setState(BuildResource.State.Compiling);
							step = new CompileStep(child, compiler.createCompiler(child.getSource()));
							completionService.submit(step);
						}
					}
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Build job finished");
		return buildRepository.getBuildResources();
	}

	private void log(String message) {
		System.out.println(message);
	}

}
