/**
 * 
 */
package com.jeffreyricker.osgi.resolver.impl;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.resolver.BundleResolver;
import com.jeffreyricker.osgi.resolver.ResolverJob;

/**
 * @author Ricker
 * @date Apr 27, 2011
 *
 */
public class BundleResolverImpl implements BundleResolver {

	@Override
	public ResolverJob createResolver(Repository repository, Resource resource) {
		return new ResolverJobImpl(repository, resource);
	}

}
