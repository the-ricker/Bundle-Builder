/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.util.Set;

import com.jeffreyricker.osgi.builder.BuildResource;

/**
 * @author Ricker
 * @date Apr 29, 2011
 *
 */
public interface BuildRepository {
	
	public Set<BuildResource> getBuildResources();
	
	public boolean isBuilding();
	
	public boolean hasWork();
	
	

}
