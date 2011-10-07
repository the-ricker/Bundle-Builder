package com.jeffreyricker.osgi.builder;

import java.util.Set;
import java.util.concurrent.Callable;

/**
 * An callable job that yields the results of a bundle build.
 * 
 * @author ricker
 *
 */
public interface BundleBuilderJob extends Callable<Set<BuildResource>>{

}
