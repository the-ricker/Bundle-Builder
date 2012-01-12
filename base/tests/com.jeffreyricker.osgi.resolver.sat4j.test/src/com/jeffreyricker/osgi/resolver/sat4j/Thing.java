/**
 * 
 */
package com.jeffreyricker.osgi.resolver.sat4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jricker
 * 
 */
public class Thing extends Named {

	public List<Req> requirements = new ArrayList<Req>();

	public List<Cap> capabilities = new ArrayList<Cap>();

	public Thing(String name) {
		super(name);
	}

}
