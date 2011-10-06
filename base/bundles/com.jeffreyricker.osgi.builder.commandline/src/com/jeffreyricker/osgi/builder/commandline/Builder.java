package com.jeffreyricker.osgi.builder.commandline;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.log.LogService;
import org.osgi.service.obr.Repository;

import com.jeffreyricker.osgi.builder.BundleBuilder;
import com.jeffreyricker.osgi.builder.BundleBuilderJob;
import com.jeffreyricker.osgi.builder.source.BundleSource;
import com.jeffreyricker.osgi.builder.source.BundleSourceFactory;
import com.jeffreyricker.osgi.builder.util.SimpleBuildInstructions;
import com.jeffreyricker.osgi.repository.FileRepository;

public class Builder implements Runnable {

	private BundleContext context;
	private BundleBuilder builder;
	private BundleSourceFactory factory;
	private LogService log;

	public Builder(BundleContext context, BundleBuilder builder, BundleSourceFactory factory) {
		this.context = context;
		this.builder = builder;
		this.factory = factory;
	}

	@Override
	public void run() {
		try {
			SimpleBuildInstructions instructions = new SimpleBuildInstructions();
			//
			String repositoryName = context.getProperty(Constants.REPOSITORY);
			log("Repository = " + repositoryName);
			Repository repository = new FileRepository(new File(repositoryName));
			instructions.setRepository(repository);
			//
			String outputName = context.getProperty(Constants.OUTPUT);
			log("Output = " + outputName);
			instructions.setOutputDirectory(new File(outputName));
			//
			String qualifierName = context.getProperty(Constants.QUALIFIER);
			log("Qualifier = " + qualifierName);
			if (qualifierName != null) {
				instructions.setVersionQualifier(qualifierName);
			}
			//
			String sources = context.getProperty(Constants.SOURCES);
			log("Sources = " + sources);
			Set<BundleSource> sourceSet = new HashSet<BundleSource>();
			for (String source : sources.split(",")) {
				File file = new File(source);
				Set<BundleSource> bs = factory.createSource(file);
				sourceSet.addAll(bs);
			}
			instructions.setBundleSources(sourceSet);
			// call it
			BundleBuilderJob job = builder.createBuilder(instructions);
			job.call();
		} catch (Exception e) {
			log(e.getLocalizedMessage());
		}
	}

	private void log(String message) {
		if (log != null) {
			log.log(LogService.LOG_INFO, message);
		} else {
			System.out.println(message);
		}
	}

}
