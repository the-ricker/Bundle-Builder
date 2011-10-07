package com.jeffreyricker.osgi.builder.impl;

import java.util.concurrent.Callable;

import com.jeffreyricker.osgi.builder.BuildResource;

/**
 * Abstract step in the build process.
 * 
 * @author ricker
 *
 */
public interface BuildStep extends Callable<BuildResource>{

}
