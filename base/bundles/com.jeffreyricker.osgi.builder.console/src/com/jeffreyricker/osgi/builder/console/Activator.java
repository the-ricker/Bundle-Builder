package com.jeffreyricker.osgi.builder.console;

import java.util.Properties;

import javax.servlet.Servlet;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator, ServiceTrackerCustomizer {

	private BundleContext context;
//	private ServiceTracker tracker;
	private HttpService http;

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
//		tracker = new ServiceTracker(context, Servlet.class.getName(), this);
//		tracker.open();
		Properties props = new Properties();
		props.put("felix.webconsole.label", "builder");
		context.registerService(Servlet.class.getName(),new BuilderServlet(), props);
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		context = null;
	}

	@Override
	public Object addingService(ServiceReference reference) {
		Object obj = context.getService(reference);
		if (obj instanceof HttpService) {
			http = (HttpService) obj;
			startServlets();
			return http;
		}
		return null;
	}

	private void startServlets() {
		try {
			http.registerResources(Constants.ROOT_URL, Constants.RESOURCES_DIR, null);
			Properties props = new Properties();
			props.put("felix.webconsole.label", "builder");
			http.registerServlet(Constants.BASE_SERVLET_ALIAS, new BuilderServlet(), props, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void modifiedService(ServiceReference reference, Object service) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removedService(ServiceReference reference, Object service) {

	}

}
