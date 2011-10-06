/**
 * 
 */
package com.jeffreyricker.osgi.resolver;

import java.util.concurrent.Callable;

/**
 * A job to resolve a given resource. The factory method for this job is
 * {@link BundleResolver#createResolver(org.osgi.service.obr.Repository, org.osgi.service.obr.Resource)}
 * .
 * 
 * @author Ricker
 * @date Apr 29, 2011
 * 
 */
public interface ResolverJob extends Callable<Solution> {

}
