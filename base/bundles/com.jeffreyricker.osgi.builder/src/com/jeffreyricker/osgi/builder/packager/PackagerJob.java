/**
 * 
 */
package com.jeffreyricker.osgi.builder.packager;

import java.io.File;
import java.util.concurrent.Callable;

/**
 * A callable job that has the results of packaging.
 * 
 * @author Ricker
 * @date Apr 29, 2011
 *
 */
public interface PackagerJob extends Callable<File> {

}
