package com.jeffreyricker.osgi.builder.commandline;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.jeffreyricker.osgi.builder.BundleBuilder;
import com.jeffreyricker.osgi.builder.source.BundleSourceFactory;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

	private BundleContext context;

	private ServiceTracker builderTracker;
	private BundleBuilder builder;
//	private LogService log;
//	private ServiceTracker repoAdminTracker;
//	private RepositoryAdmin repoAdmin;

	private ServiceTracker factoryTracker;
	private BundleSourceFactory factory;
	private String factoryName;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
//		ServiceTracker logTracker = new ServiceTracker(context, LogService.class.getName(), null);
//		logTracker.open();
//		log = (LogService) logTracker.getService();
		//
//		repoAdminTracker = new ServiceTracker(context, RepositoryAdmin.class.getName(), this);
//		repoAdminTracker.open();
		//
		builderTracker = new ServiceTracker(context, BundleBuilder.class.getName(), this);
		builderTracker.open();
		//
		factoryName = context.getProperty(Constants.SOURCE_FACTORY);
		factoryTracker = new ServiceTracker(context, BundleSourceFactory.class.getName(), this);
		factoryTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		context = null;
	}

	private void build() throws Exception {
		if (factory == null || builder == null) {
			return;
		}
		Thread thread = new Thread(new Builder(context,builder,factory));
		thread.start();
//		ExecutorService executor = Executors.newCachedThreadPool();
//		SimpleBuildInstructions instructions = new SimpleBuildInstructions();
//		//
//		String repositoryName = context.getProperty(Constants.REPOSITORY);
//		log("Repository = " + repositoryName);
//		Repository repository = new FileRepository(new File(repositoryName));
//		instructions.setRepository(repository);
//		//
//		String outputName = context.getProperty(Constants.OUTPUT);
//		log("Output = " + outputName);
//		instructions.setOutputDirectory(new File(outputName));
//		//
//		String qualifierName = context.getProperty(Constants.QUALIFIER);
//		log("Qualifier = " + qualifierName);
//		if (qualifierName != null) {
//			instructions.setVersionQualifier(qualifierName);
//		}
//		//
//		String sources = context.getProperty(Constants.SOURCES);
//		log("Sources = " + sources);
//		Set<BundleSource> sourceSet = new HashSet<BundleSource>();
//		for (String source : sources.split(",")) {
//			File file = new File(source);
//			Set<BundleSource> bs = factory.createSource(file);
//			sourceSet.addAll(bs);
//		}
//		instructions.setBundleSources(sourceSet);
//		// call it
//		executor.submit(builder.createBuilder(instructions));
	}
	

	


	@Override
	public Object addingService(ServiceReference reference) {
		Object obj = context.getService(reference);
//		if (obj instanceof RepositoryAdmin) {
//			repoAdmin = (RepositoryAdmin) obj;
//			try {
//				build();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			return obj;
//		}
		if (obj instanceof BundleBuilder) {
			builder = (BundleBuilder) obj;
			try {
				build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
		}
		if (obj instanceof BundleSourceFactory) {
			if (factoryName != null && !factoryName.equals(obj.getClass().getName())) {
				return null;
			}
			factory = (BundleSourceFactory) obj;
			try {
				build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
		}
		return null;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub

	}

}
