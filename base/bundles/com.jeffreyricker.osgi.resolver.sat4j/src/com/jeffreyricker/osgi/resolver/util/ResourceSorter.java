/**
 * 
 */
package com.jeffreyricker.osgi.resolver.util;

import java.util.Comparator;

import org.osgi.service.obr.Resource;

/**
 * @author jricker
 *
 */
public class ResourceSorter implements Comparator<Resource> {

	@Override
	public int compare(Resource left, Resource right) {
		if (left == null) {
			if (right == null) {
				return 0;
			}
			return -1;
		}
		if (right == null) {
			return 1;
		}
		// sort names A to Z
		int i = left.getSymbolicName().compareTo(right.getSymbolicName());
		if (i == 0) {
			// sort versions from latest to earliest
			i = -1 * left.getVersion().compareTo(right.getVersion());
		}
		return i;
	}

}
