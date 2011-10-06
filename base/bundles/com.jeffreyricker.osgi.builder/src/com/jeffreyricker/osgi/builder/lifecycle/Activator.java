package com.jeffreyricker.osgi.builder.lifecycle;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import com.jeffreyricker.osgi.builder.BundleBuilder;
import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.compiler.impl.BundleCompilerImpl;
import com.jeffreyricker.osgi.builder.impl.BundleBuilderImpl;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.builder.packager.impl.BundlePackagerImpl;
import com.jeffreyricker.osgi.builder.source.BundleSourceFactory;
import com.jeffreyricker.osgi.builder.source.util.EclipseBundleSourceFactory;
import com.jeffreyricker.osgi.resolver.BundleResolver;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

	private BundleContext context;
	private BundleBuilderImpl builderImpl;
	private ServiceRegistration eclipse;
	private ServiceRegistration packager;
	private ServiceRegistration compiler;
	private ServiceRegistration builder;
	private ServiceTracker tracker;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		// compiler
		BundleCompiler compilerImpl = new BundleCompilerImpl();
		// packager
		BundlePackager packagerImpl = new BundlePackagerImpl();
		// builder
		builderImpl = new BundleBuilderImpl();
		builderImpl.setCompiler(compilerImpl);
		builderImpl.setPackager(packagerImpl);
		// resolver
		tracker = new ServiceTracker(context, BundleResolver.class.getName(), this);
		tracker.open();
		// register
		compiler = context.registerService(BundleCompiler.class.getName(), compilerImpl, getCompilerProps());
		packager = context.registerService(BundlePackager.class.getName(), packagerImpl, getPackagerProps());
		builder = context.registerService(BundleBuilder.class.getName(), builderImpl, getBuilderProps());
		eclipse = context.registerService(BundleSourceFactory.class.getName(), new EclipseBundleSourceFactory(),
				getEclipseProps());
	}

	private Properties getEclipseProps() {
		// TODO Auto-generated method stub
		return null;
	}

	private Properties getBuilderProps() {
		// TODO Auto-generated method stub
		return null;
	}

	private Properties getPackagerProps() {
		// TODO Auto-generated method stub
		return null;
	}

	private Properties getCompilerProps() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		tracker.close();
		tracker = null;
		builder.unregister();
		builder = null;
		packager.unregister();
		packager = null;
		compiler.unregister();
		compiler = null;
		eclipse.unregister();
		eclipse = null;
		builderImpl = null;
		context = null;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		Object obj = context.getService(reference);
		if (obj instanceof BundleResolver && builderImpl.getResolver() == null) {
			builderImpl.setResolver((BundleResolver) obj);
			return obj;
		}
		return null;
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {

	}

	@Override
	public void removedService(ServiceReference reference, Object service) {
		if (builderImpl.getResolver() == service) {
			builderImpl.setResolver(null);
		}
	}

}
