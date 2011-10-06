package com.jeffreyricker.osgi.builder.ant;

import java.io.File;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class BundleBuilderTask extends Task {
	
	private boolean parallel;
	private String qualifier;
	private String sourceFactory;
	
	public BundleBuilderTask() {
		parallel = true;
	}
	
	public void setDestdir(File dir) {
		
	}
	
	public void setRepository(String url){
		
	}
	
	public void setQualifier(String qualifier) {
		
	}
	
	public void setParallel(boolean parallel) {
		this.parallel = parallel;
	}
	
	public File createBundle() {
		return null;
	}
	
	public void addBundle(File bundle){
		
	}
	
	public void addConfiguredBundle(File bundle) {
		
	}
	
	public void setSourceFactory(String sourceFactory) {
		
	}
	
	@Override
	public void execute() throws BuildException {
		
	}

}
