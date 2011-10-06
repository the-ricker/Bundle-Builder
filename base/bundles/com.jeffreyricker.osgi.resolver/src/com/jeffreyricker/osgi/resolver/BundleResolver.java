/**
 * 
 */
package com.jeffreyricker.osgi.resolver;

import org.osgi.service.obr.Repository;
import org.osgi.service.obr.Resource;

/**
 * A bundle resolve will resolve the dependencies of a given resource against a
 * given repository. The resource may be but does not have to be a member of the
 * repository.
 * 
 * @author Ricker
 * 
 */
public interface BundleResolver {

	public ResolverJob createResolver(Repository repository, Resource resource);

}
