package com.jeffreyricker.osgi.builder.compiler.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.jeffreyricker.osgi.builder.compiler.CompilerJob;
import com.jeffreyricker.osgi.builder.compiler.util.ClassPaths;
import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * Must run with JDK. Will throw a null pointer exception if run on JRE.
 * @author Ricker
 * @date May 5, 2011
 */
public class CompilerJobImpl implements CompilerJob {

	private BundleSource source;

	public CompilerJobImpl(BundleSource source) {
		this.source = source;
	}

	@Override
	public List<Diagnostic<? extends JavaFileObject>> call() throws Exception {
		if (source.getDependencies() == null) {
			throw new IllegalStateException("Bundle source not in resolved state");
		}
		deleteoutput();
		// things we need
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		if (compiler == null) {
			throw new IllegalStateException("No tool provider. Must run with JDK.");
		}
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileMgr = compiler.getStandardFileManager(diagnostics, null, null);
		List<String> options = new ArrayList<String>();
		// destination
		options.add("-d");
		options.add(source.getOutput().toString());
		// classpath
		options.add("-cp");
		options.add(ClassPaths.createClassPath(source.getDependencies()));
		// all packages in source folders
		List<File> files = new ArrayList<File>();
		for (File f : source.getSources()) {
			files.addAll(ClassPaths.findJavaFiles(f));
		}
		Iterable<? extends JavaFileObject> fileObjects = fileMgr.getJavaFileObjects(files.toArray(new File[] {}));
		// execute
		CompilationTask task = compiler.getTask(null, fileMgr, diagnostics, options, null, fileObjects);
		task.call();
		fileMgr.close();
		return diagnostics.getDiagnostics();
	}

	private void deleteoutput() {
		File bin = source.getOutput();
		if (bin.exists()) {
			bin.delete();
		}
		bin.mkdir();
	}

}
