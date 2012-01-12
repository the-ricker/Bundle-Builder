/**
 * 
 */
package com.jeffreyricker.osgi.builder.impl;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.jeffreyricker.osgi.builder.compiler.BundleCompiler;
import com.jeffreyricker.osgi.builder.compiler.CompilerJob;
import com.jeffreyricker.osgi.builder.packager.BundlePackager;
import com.jeffreyricker.osgi.builder.packager.PackagerJob;
import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * Creates jobs that wait a random time and returns null.
 * 
 * @author Ricker
 * @date May 6, 2011
 */
public class DummyJobFactory implements BundleCompiler, BundlePackager {

	Random random = new Random(System.currentTimeMillis());
	int MAXPAUSE = 1000;

	@Override
	public CompilerJob createCompiler(BundleSource source) {
		return new CompilerJob(){
			@Override
			public List<Diagnostic<? extends JavaFileObject>> call() throws Exception {
				Thread.sleep(random.nextInt(MAXPAUSE));
				return null;
			}
			
		};
	}

	@Override
	public PackagerJob createPackager(BundleSource source, File targetDir) {
		return new PackagerJob(){
			@Override
			public File call() throws Exception {
				Thread.sleep(random.nextInt(MAXPAUSE));
				return null;
			}
		};
	}

	
}
