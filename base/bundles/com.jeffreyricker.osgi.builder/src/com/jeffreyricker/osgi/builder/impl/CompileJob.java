package com.jeffreyricker.osgi.builder.impl;

import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.jeffreyricker.osgi.builder.BuildResource;
import com.jeffreyricker.osgi.builder.compiler.CompilerJob;

public class CompileJob implements BuildJob {

	private BuildResource resource;
	private CompilerJob compiler;

	public CompileJob(BuildResource resource, CompilerJob compiler) {
		this.resource = resource;
		this.compiler = compiler;
	}

	@Override
	public BuildResource call() throws Exception {
		resource.setState(BuildResource.State.Compiling);
		try {
			List<Diagnostic<? extends JavaFileObject>> results = compiler.call();
			resource.setCompilerResults(results);
			// TODO do we need to look at the results for failure?
			resource.setState(BuildResource.State.Compiled);
		} catch (Exception e) {
			e.printStackTrace();
			resource.setState(BuildResource.State.FailedCompile);
		}
		return resource;
	}

}
