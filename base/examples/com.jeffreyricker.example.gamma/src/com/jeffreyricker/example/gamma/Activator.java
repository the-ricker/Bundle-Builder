package com.jeffreyricker.example.gamma;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.jeffreyricker.example.alpha.Apple;
import com.jeffreyricker.example.beta.Mcintosh;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		Apple apple = new Mcintosh();
		if (apple != null) {
			
		}
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
