package com.jeffreyricker.osgi.resolver.lifecycle;

import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import com.jeffreyricker.osgi.resolver.BundleResolver;
import com.jeffreyricker.osgi.resolver.impl.BundleResolverImpl;

public class Activator implements BundleActivator {

	private BundleContext context;

	private ServiceRegistration registration;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		Properties props = new Properties();
		// TODO add props
		registration = context.registerService(BundleResolver.class.getName(), new BundleResolverImpl(), props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
		registration = null;
		context = null;
	}

}
