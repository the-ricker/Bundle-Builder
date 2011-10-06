/**
 * 
 */
package com.jeffreyricker.osgi.builder;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.osgi.service.obr.Resource;

import com.jeffreyricker.osgi.builder.source.BundleSource;

/**
 * @author Ricker
 * 
 */
public interface BuildResource extends Resource {

	/**
	 * The state of the build resource in the bundle build process.
	 * 
	 * @author Ricker
	 * @date May 2, 2011
	 */
	public enum State {

		/**
		 * The dependencies are NOT set
		 */
		Unresolved,

		Resolving,

		FailedResolve,

		/**
		 * The dependencies are set
		 */
		Resolved,

		/**
		 * All dependencies are built TODO should this be a state?
		 */
		// Ready,

		/**
		 * The compilation is currently running
		 */
		Compiling,

		/**
		 * The compilation is complete and failed
		 */
		FailedCompile,

		/**
		 * The compilation is complete and succeeded
		 */
		Compiled,

		Packaging,

		FailedPackaging,

		Built;

		/**
		 * The resource is still working
		 * 
		 * @return
		 */
		public boolean isWorking() {
			switch (this) {
			case Unresolved:
			case Resolving:
			case Resolved:
				// case Ready:
			case Compiling:
			case Compiled:
			case Packaging:
				return true;
			case FailedCompile:
			case FailedResolve:
			case FailedPackaging:
			case Built:
			default:
				return false;
			}
		}

		/**
		 * The resource has more steps
		 * 
		 * @return
		 */
		public boolean hasWork() {
			switch (this) {
			case Unresolved:
			case Resolving:
			case Resolved:
				// case Ready:
			case Compiling:
			case Compiled:
				return true;
			case Packaging:
			case FailedCompile:
			case FailedResolve:
			case FailedPackaging:
			case Built:
			default:
				return false;
			}
		}

		/**
		 * The resource has failed to build
		 * 
		 * @return
		 */
		public boolean isFailed() {
			switch (this) {
			case FailedCompile:
			case FailedResolve:
			case FailedPackaging:
				return true;
			case Unresolved:
			case Resolving:
			case Resolved:
				// case Ready:
			case Compiling:
			case Compiled:
			case Packaging:
			case Built:
			default:
				return false;
			}
		}

	}

	public State getState();

	public void setState(State state);

	public BundleSource getSource();

	public Set<BuildResource> getParents();

	public Set<BuildResource> getChildren();

	public List<Diagnostic<? extends JavaFileObject>> getCompilerResults();

	public void setCompilerResults(List<Diagnostic<? extends JavaFileObject>> results);

	public List<String> getResolverResults();

	public void setResolverResults(List<String> results);

	public File getFile();

	public void setFile(File jar);

	/**
	 * True if all the dependencies that are build resources are built. This is
	 * not a state of this resource because it is dependent upon the state of
	 * other resources.
	 * 
	 * @return
	 */
	public boolean isReady();

}
