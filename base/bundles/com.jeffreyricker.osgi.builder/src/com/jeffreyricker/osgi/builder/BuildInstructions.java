package com.jeffreyricker.osgi.builder;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.osgi.service.obr.Repository;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * The set of instructions for the builder, including what to build and how to
 * build it.
 * 
 * @author Ricker
 * @date Apr 27, 2011
 * 
 */
public interface BuildInstructions {

	/**
	 * OSGi bundle version numbers have four parts:
	 * <ol>
	 * <li>major</li>
	 * <li>minor</li>
	 * <li>micro</li>
	 * <li>qualifier</li>
	 * </ol>
	 * 
	 * In Eclipse, by convention we place the string "qualifier" in the
	 * qualifier position to indicate that the qualifier is specified in the
	 * build process. Usually the qualifier is a time stamp, but it can be a
	 * build number or any other text.
	 * 
	 * @return the qualifier for the bundle version
	 */
	public String getVersionQualifier();

	/**
	 * The repository to resolve dependencies.
	 * 
	 * @return
	 */
	public Repository getRepository();

	/**
	 * The local file location to write the build results.
	 * 
	 * @return
	 */
	public File getOutputDirectory();

	/**
	 * 
	 * @return
	 * @throws MalformedURLException
	 */
	public URL getOutputURL() throws MalformedURLException;

	/**
	 * The bundles can be built sequentially or in parallel.
	 * <p>
	 * Suppose there are three bundles, A B and C, to be built. Further suppose
	 * that bundle B depends on bundle A and bundle C has no dependencies.
	 * <p>
	 * In sequential mode, only one bundle will be built at a time. The builder
	 * will build A then B then C.
	 * <p>
	 * In parallel mode, the builder will build all bundles simultaneously for
	 * which the dependencies are built. So it would build A and C right away and then
	 * build B when A is built.
	 * 
	 * @return true if builder should run in parallel
	 */
	public boolean isParallel();

	/**
	 * The bundles to build.
	 * 
	 * @return
	 */
	public Set<BundleSource> getBundleSources();

}
